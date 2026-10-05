package ua.notky.silfy.ui.fragment.words

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import ua.notky.base.extension.hideKeyboard
import ua.notky.base.extension.observe
import ua.notky.base.extension.openSafeScreen
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.R
import ua.notky.silfy.databinding.FragmentWordsBinding
import ua.notky.silfy.models.enums.DictionaryTab
import ua.notky.silfy.models.model.Profile
import ua.notky.silfy.models.model.Word
import ua.notky.silfy.models.model.WordCounts
import ua.notky.silfy.ui.adapter.decorators.ListCardDividerDecoration
import ua.notky.silfy.ui.view.setLightSystemBars
import ua.notky.silfy.viewmodel.words.WordsViewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

/** 2a/2b Dictionary: search, sort A–Z / Z–A / Level, tabs All / Favourites / Blacklist */
class WordsFragment : BaseBindingFragment<FragmentWordsBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentWordsBinding
        get() = FragmentWordsBinding::inflate

    private val wordsViewModel by activityViewModels<WordsViewModel>()

    private val wordAdapter = DictionaryWordAdapter { goToNextEdit(it) }

    /** Tab, search or sort changed: show the new list from its first word */
    private var scrollToTop = false

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(wordsViewModel)
            .build()
    }

    override fun onResume() {
        super.onResume()
        setLightSystemBars(true)
    }

    override fun initializeViews() {
        applyInsets()

        binding.recycler.adapter = wordAdapter
        binding.recycler.itemAnimator = null
        binding.recycler.addItemDecoration(ListCardDividerDecoration(requireContext()))

        binding.tabs.setOptions(
            listOf(
                getString(R.string.dictionary_tab_all),
                getString(R.string.list_favourites),
                getString(R.string.list_blacklist)
            )
        )

        // Before the text listener: the restored search must not reset the list position
        wordsViewModel.filter.value?.let { binding.editSearch.setText(it.search) }
    }

    override fun initializeListeners() {
        binding.buttonAdd.setOnClickListener { goToNextEdit(null) }

        binding.buttonSort.setOnClickListener {
            scrollToTop = true
            wordsViewModel.cycleSort()
        }

        binding.tabs.setOnOptionSelectedListener { index ->
            scrollToTop = true
            wordsViewModel.selectTab(DictionaryTab.values()[index])
        }

        binding.editSearch.doAfterTextChanged {
            val pattern = it?.toString().orEmpty()
            // Restored text of a recreated view is the same search: keep the list position
            if (pattern != wordsViewModel.filter.value?.search) {
                scrollToTop = true
                wordsViewModel.search(pattern)
            }
        }

        binding.editSearch.setOnEditorActionListener { view, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) view.hideKeyboard()
            false
        }
    }

    override fun initializeViewModels() {
        viewLifecycleOwner.observe(wordsViewModel.profile, ::renderProfile)
        viewLifecycleOwner.observe(wordsViewModel.counts, ::renderCounts)
        viewLifecycleOwner.observe(wordsViewModel.filter, ::renderFilter)
        viewLifecycleOwner.observe(wordsViewModel.words, ::renderWords)
    }

    /**
     * Status bar on top. The list card ends above the floating navigation,
     * or above the keyboard while searching (the navigation is under the keyboard then).
     */
    private fun applyInsets() {
        val initialTop = binding.root.paddingTop
        val navInset = resources.getDimensionPixelSize(R.dimen.ds_nav_content_inset)
        val keyboardGap = resources.getDimensionPixelSize(R.dimen.ds_card_gap_large)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val ime = insets.getInsets(WindowInsetsCompat.Type.ime())

            view.updatePadding(top = initialTop + bars.top)
            binding.cardWords.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                bottomMargin = if (ime.bottom > bars.bottom) ime.bottom + keyboardGap else bars.bottom + navInset
            }
            insets
        }
        ViewCompat.requestApplyInsets(binding.root)
    }

    private fun renderProfile(profile: Profile?) {
        binding.textGreeting.text = profile?.let { getString(R.string.dictionary_greeting, it.name) }
    }

    private fun renderCounts(counts: WordCounts?) {
        val value = counts ?: WordCounts()
        binding.tabs.setCounts(listOf(value.total, value.favourites, value.blacklist))
    }

    private fun renderFilter(filter: WordsViewModel.Filter?) {
        filter ?: return
        binding.buttonSort.setText(filter.sort.label)
        binding.tabs.selectedIndex = filter.tab.ordinal
    }

    private fun renderWords(words: List<Word>?) {
        val list = words.orEmpty()

        wordAdapter.submitList(list) {
            // The callback may come after the view is destroyed
            if (scrollToTop && view != null) {
                scrollToTop = false
                binding.recycler.scrollToPosition(0)
            }
        }

        binding.recycler.isVisible = list.isNotEmpty()
        binding.viewEmpty.isVisible = list.isEmpty()
        if (list.isEmpty()) binding.textEmptyHint.setText(emptyHint())
    }

    private fun emptyHint(): Int {
        val filter = wordsViewModel.filter.value ?: WordsViewModel.Filter()

        return when {
            filter.search.isNotBlank() -> R.string.dictionary_empty_search
            filter.tab == DictionaryTab.FAVOURITES -> R.string.dictionary_empty_favourites
            filter.tab == DictionaryTab.BLACKLIST -> R.string.dictionary_empty_blacklist
            else -> R.string.dictionary_empty_all
        }
    }

    private fun goToNextEdit(word: Word?) {
        binding.editSearch.hideKeyboard()
        openSafeScreen(
            WordsFragmentDirections.actionFragmentWordsToFragmentWordsEdit()
                .setWordId(word?.id ?: NEW_WORD_ID)
        )
    }

    private companion object {
        const val NEW_WORD_ID = -1
    }
}

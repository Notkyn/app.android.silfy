package ua.notky.silfy.ui.fragment.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ua.notky.base.extension.findSafeNavController
import ua.notky.base.extension.observe
import ua.notky.base.extension.openSafePopBackstackScreen
import ua.notky.base.extension.openSafeScreen
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.R
import ua.notky.silfy.databinding.FragmentCategoryOverviewBinding
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.models.model.Word
import ua.notky.silfy.models.states.CategoryDeleteUiState
import ua.notky.silfy.ui.adapter.decorators.ListCardDividerDecoration
import ua.notky.silfy.ui.dialog.DialogTone
import ua.notky.silfy.ui.dialog.category.EditCategoryBottomsheet
import ua.notky.silfy.ui.dialog.showSilfyDialog
import ua.notky.silfy.ui.fragment.words.DictionaryWordAdapter
import ua.notky.silfy.ui.view.avatar.setCategoryTile
import ua.notky.silfy.ui.view.setLightSystemBars
import ua.notky.silfy.viewmodel.category.CategoryOverviewViewModel

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 15.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

/** 3b Category: words A–Z, 3c rename sheet, 3d "Delete this category?", "Add word" — a new word in this category */
@AndroidEntryPoint
class CategoryOverviewFragment : BaseBindingFragment<FragmentCategoryOverviewBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCategoryOverviewBinding
        get() = FragmentCategoryOverviewBinding::inflate

    private val categoryOverviewViewModel by viewModels<CategoryOverviewViewModel>()

    private val wordAdapter = DictionaryWordAdapter(showLevelName = false) { goToNextEdit(it) }

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(categoryOverviewViewModel)
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
    }

    override fun initializeListeners() {
        binding.buttonBack.setOnClickListener { openSafePopBackstackScreen() }
        binding.buttonEdit.setOnClickListener {
            EditCategoryBottomsheet.show(childFragmentManager, categoryOverviewViewModel.categoryId)
        }
        binding.buttonDelete.setOnClickListener { showDeleteCategoryDialog() }
        binding.buttonAddWord.setOnClickListener { goToNextEdit(null) }
    }

    override fun initializeViewModels() {
        // Nullable: not through observe()
        categoryOverviewViewModel.category.observe(viewLifecycleOwner) { renderCategory(it) }
        viewLifecycleOwner.observe(categoryOverviewViewModel.words, ::renderWords)
        viewLifecycleOwner.observe(categoryOverviewViewModel.uiState, ::renderUiState)
    }

    /** Status bar on top; the floating button and the end of the card stay above the navigation bar */
    private fun applyInsets() {
        val fabMargin = resources.getDimensionPixelSize(R.dimen.ds_fab_margin_bottom)
        val cardGap = resources.getDimensionPixelSize(R.dimen.ds_card_gap_large)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.updatePadding(top = bars.top)
            binding.buttonAddWord.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                bottomMargin = fabMargin + bars.bottom
            }
            // The card ends above "Add word", so the last word is never under it
            binding.cardWords.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                bottomMargin = fabMargin + bars.bottom + binding.buttonAddWord.minimumHeight + cardGap
            }
            insets
        }
        ViewCompat.requestApplyInsets(binding.root)
    }

    /** null — the category was deleted (here or by another screen): nothing to show */
    private fun renderCategory(category: Category?) {
        if (category == null) {
            closeOnce()
            return
        }

        binding.textInitial.setCategoryTile(category.id, category.title)
        binding.textName.text = category.title
    }

    private fun renderWords(words: List<Word>?) {
        val list = words.orEmpty()

        wordAdapter.submitList(list)
        binding.textCount.text = resources.getQuantityString(R.plurals.plural_words, list.size, list.size)
        binding.recycler.isVisible = list.isNotEmpty()
        binding.textEmpty.isVisible = list.isEmpty()
    }

    private fun renderUiState(state: CategoryDeleteUiState?) {
        binding.buttonDelete.isEnabled = state == CategoryDeleteUiState.Idle

        when (state) {
            CategoryDeleteUiState.Deleted -> closeOnce()
            CategoryDeleteUiState.Failure -> {
                categoryOverviewViewModel.consumeState()
                showSilfyDialog(
                    icon = R.drawable.ic_lc_triangle_alert,
                    tone = DialogTone.WARNING,
                    title = getString(R.string.category_error_delete),
                    message = getString(R.string.create_error_message),
                    cancelText = null
                )
            }
            else -> {}
        }
    }

    /** After a delete both the result and the live category say "gone": go back to the grid only once */
    private fun closeOnce() {
        if (findSafeNavController()?.currentDestination?.id == R.id.fragment_category_overview) {
            openSafePopBackstackScreen()
        }
    }

    private fun showDeleteCategoryDialog() {
        showSilfyDialog(
            icon = R.drawable.ic_lc_trash_2,
            tone = DialogTone.DANGER,
            title = getString(R.string.category_delete_title),
            message = getString(R.string.category_delete_message),
            okText = getString(R.string.category_delete_button),
            destructive = true,
            onOk = { categoryOverviewViewModel.delete() }
        )
    }

    /** A word of the list — its form; null — a new word, already in this category */
    private fun goToNextEdit(word: Word?) {
        openSafeScreen(
            CategoryOverviewFragmentDirections.actionFragmentCategoryOverviewToFragmentWordsEdit()
                .setWordId(word?.id ?: NEW_WORD_ID)
                .setCategoryId(if (word == null) categoryOverviewViewModel.categoryId else NO_CATEGORY_ID)
        )
    }

    private companion object {
        const val NEW_WORD_ID = -1
        const val NO_CATEGORY_ID = -1
    }
}

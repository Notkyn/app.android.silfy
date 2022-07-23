package ua.notky.silfy.ui.fragment.words

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import ua.notky.base.extension.observe
import ua.notky.base.extension.openSafeScreen
import ua.notky.base.ui.adapter.extensions.doOnRootClick
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.databinding.FragmentWordsBinding
import ua.notky.silfy.models.model.Word
import ua.notky.silfy.ui.adapter.WordAdapter
import ua.notky.silfy.viewmodel.StateViewModel
import ua.notky.silfy.viewmodel.words.WordsEditViewModel
import ua.notky.silfy.viewmodel.words.WordsViewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class WordsFragment : BaseBindingFragment<FragmentWordsBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentWordsBinding
        get() = FragmentWordsBinding::inflate

    private val stateViewModel by activityViewModels<StateViewModel>()
    private val wordsViewModel by activityViewModels<WordsViewModel>()
    private val wordsEditViewModel by activityViewModels<WordsEditViewModel>()

    private val wordAdapter: WordAdapter by lazy {
        return@lazy WordAdapter()
    }

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(wordsViewModel)
            .build()
    }

    override fun initializeViews() {
        binding.state = stateViewModel.state
        binding.model = wordsViewModel.model

        initializeRecycler()

        binding.searchLayout.setModel(stateViewModel.state)
        binding.viewHeader.selectTab(wordsViewModel.indexTab)
    }

    private fun initializeRecycler() {
        binding.recycler.adapter = wordAdapter
        binding.recycler.itemAnimator = DefaultItemAnimator()

        wordAdapter.doOnRootClick { goToNextEdit(it) }
    }

    override fun initializeListeners() {
        initSortListeners()
        initializeTabLayoutListener()

        binding.buttonFab.setOnClickListener {
            goToNextEdit(null)
        }

        binding.searchLayout.handleSearchPattern {
            wordsViewModel.onRefreshWords(binding.viewSort.getSortParams(), it)
        }
    }

    override fun initializeViewModels() {
        if (wordsViewModel.isEmptyData()) {
            stateViewModel.clearSearch()
            binding.searchLayout.clearSearch()
            stateViewModel.setDefaultSort()
            wordsViewModel.onRefreshWords(binding.viewSort.getSortParams())
        }

        observe(wordsViewModel.words, ::renderListWords)
    }

    private fun initSortListeners() {
        binding.viewSort.handleSortEnClick {
            stateViewModel.setEnSort()
            wordsViewModel.onSortWords(
                binding.viewSort.getSortParams(),
                binding.searchLayout.getSearchPattern()
            )
        }
        binding.viewSort.handleSortRuClick {
            stateViewModel.setRuSort()
            wordsViewModel.onSortWords(
                binding.viewSort.getSortParams(),
                binding.searchLayout.getSearchPattern()
            )
        }
        binding.viewSort.handleSortTypeClick {
            stateViewModel.setTypeSort()
            wordsViewModel.onSortWords(
                binding.viewSort.getSortParams(),
                binding.searchLayout.getSearchPattern()
            )
        }
        binding.viewSort.handleSortStateClick {
            stateViewModel.setStateSort()
            wordsViewModel.onSortWords(
                binding.viewSort.getSortParams(),
                binding.searchLayout.getSearchPattern()
            )
        }
    }

    private fun initializeTabLayoutListener() {
        binding.viewHeader.handleTabSelected {
            wordsViewModel.onSelectTab(
                it,
                binding.viewSort.getSortParams(),
                binding.searchLayout.getSearchPattern()
            )
        }
    }

    private fun goToNextEdit(item: Word?) {
        wordsEditViewModel.selectWord(item)
        openSafeScreen(WordsFragmentDirections.actionFragmentWordsToFragmentWordsEdit())
    }

    private fun renderListWords(words: List<Word>?) {
        words?.let { wordAdapter.clearAndAddAll(it) }
    }
}
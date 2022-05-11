package ua.notky.silfy.ui.fragment.words

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ua.notky.base.extension.addOnPropertyChanged
import ua.notky.base.extension.observe
import ua.notky.base.ui.adapter.extensions.doOnItemClick
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.util.log
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.databinding.FragmentWordsBinding
import ua.notky.silfy.models.enums.TabWords
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

    override fun init() {}

    override fun buildViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(wordsViewModel)
            .build()
    }

    override fun initViews() {
        binding.state = stateViewModel.stateModel
        binding.model = wordsViewModel.model
        binding.recycler.adapter = wordAdapter

        binding.editSearch.setTargetForCleanFocus(binding.inputSearch)
    }

    override fun initListeners() {
        initSortListeners()
        iniTabLayoutListener()

        binding.buttonFab.setOnClickListener {
            goToNextEdit(null)
        }

        wordAdapter.doOnItemClick {
            goToNextEdit(it)
        }
    }

    override fun initViewModels() {
        stateViewModel.setDefaultSort()
        wordsViewModel.clearSearch()
        wordsViewModel.onLoadAllWords()

        wordsViewModel.model.search.addOnPropertyChanged {
            this.log("initViewModels", "search", it.get())
        }

        wordsViewModel.apply { observe(wordsLiveData, ::renderListWords) }
    }

    private fun initSortListeners() {
        binding.includeSort.imageSortEn.setOnClickListener {
            stateViewModel.setEnSort()
        }

        binding.includeSort.imageSortRu.setOnClickListener {
            stateViewModel.setRuSort()
        }

        binding.includeSort.imageSortType.setOnClickListener {
            stateViewModel.setTypeSort()
        }

        binding.includeSort.imageSortState.setOnClickListener {
            stateViewModel.setStateSort()
        }
    }

    private fun iniTabLayoutListener() {
        binding.viewHeader.handleTabSelected {
            stateViewModel.setDefaultSort()
            wordsViewModel.clearSearch()

            loadingWords(it)
        }
    }

    private fun loadingWords(indexTab: Int) {
        when (indexTab) {
            TabWords.LANG.index -> wordsViewModel.onLoadAllWords()
            TabWords.FAVOURITES.index -> wordsViewModel.onLoadFavouritesWord()
            TabWords.BLACKLIST.index -> wordsViewModel.onLoadBlackListWord()
        }
    }

    private fun goToNextEdit(item: Word?) {
        wordsEditViewModel.selectWord(item)
        findNavController().navigate(WordsFragmentDirections.actionFragmentWordsToFragmentWordsEdit())
    }

    private fun renderListWords(words: List<Word>?) {
        words?.let { wordAdapter.clearAndAddAll(it) }
    }
}
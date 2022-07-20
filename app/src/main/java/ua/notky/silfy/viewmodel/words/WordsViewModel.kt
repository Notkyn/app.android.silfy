package ua.notky.silfy.viewmodel.words

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.models.enums.TabWords
import ua.notky.silfy.models.model.Word
import ua.notky.silfy.models.observable.WordsModel
import ua.notky.silfy.repository.WordRepository
import ua.notky.silfy.tools.WordSort
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@HiltViewModel
class WordsViewModel @Inject constructor(
    private val wordRepository: WordRepository
) : BaseViewModel() {
    val model: WordsModel = WordsModel()
    private var indexTab: Int = TabWords.LANG.index

    private val _words: MutableLiveData<List<Word>> = MutableLiveData()
    val words: LiveData<List<Word>> = _words

    private fun updateWordList(
        value: List<Word>,
        sortParams: WordSort.Params,
        searchPattern: String
    ) {
        val words = value.filter {
            searchPattern.isEmpty() || (it.en.contains(searchPattern, true)
                    || it.ua.contains(searchPattern, true))
        }

        _words.postValue(WordSort.sort(words, sortParams))
    }

    fun onSelectTab(index: Int, sortParams: WordSort.Params) {
        indexTab = index
        onRefreshWords(sortParams)
    }

    fun onRefreshWords(
        sortParams: WordSort.Params,
        searchPattern: String = DEFAULT_SEARCH_PATTERN
    ) {
        _words.postValue(listOf())

        when (indexTab) {
            TabWords.LANG.index -> onLoadAllWords(sortParams, searchPattern)
            TabWords.FAVOURITES.index -> onLoadFavouritesWord(sortParams, searchPattern)
            TabWords.BLACKLIST.index -> onLoadBlackListWord(sortParams, searchPattern)
        }
    }

    private fun onLoadAllWords(sortParams: WordSort.Params, searchPattern: String) {
        viewModelScope.launch {
            wordRepository.getAll()
                .catch { it.printStackTrace() }
                .collect { updateWordList(it, sortParams, searchPattern) }
        }
    }

    private fun onLoadFavouritesWord(sortParams: WordSort.Params, searchPattern: String) {
        viewModelScope.launch {
            wordRepository.getAlLFavourites()
                .catch { it.printStackTrace() }
                .collect { updateWordList(it, sortParams, searchPattern) }
        }
    }

    private fun onLoadBlackListWord(sortParams: WordSort.Params, searchPattern: String) {
        viewModelScope.launch {
            wordRepository.getAllBlacklist()
                .catch { it.printStackTrace() }
                .collect { updateWordList(it, sortParams, searchPattern) }
        }
    }

    fun onSortWords(params: WordSort.Params) {
        _words.value?.let {
            _words.postValue(
                WordSort.sort(
                    it, params
                )
            )
        }
    }

    companion object {
        private const val DEFAULT_SEARCH_PATTERN = ""
    }
}
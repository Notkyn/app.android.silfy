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
    var indexTab: Int = TabWords.LANG.index

    private val _words: MutableLiveData<List<Word>> = MutableLiveData()
    val words: LiveData<List<Word>> = _words

    fun clearData() {
        _words.postValue(null)
        indexTab = TabWords.LANG.index
    }

    fun isEmptyData() = _words.value == null

    fun onSelectTab(index: Int, sortParams: WordSort.Params, searchPattern: String) {
        indexTab = index
        onRefreshWords(sortParams, searchPattern)
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
            wordRepository.getAll(sortParams, searchPattern)
                .catch { it.printStackTrace() }
                .collect { _words.postValue(it) }
        }
    }

    private fun onLoadFavouritesWord(sortParams: WordSort.Params, searchPattern: String) {
        viewModelScope.launch {
            wordRepository.getAlLFavourites(sortParams, searchPattern)
                .catch { it.printStackTrace() }
                .collect { _words.postValue(it) }
        }
    }

    private fun onLoadBlackListWord(sortParams: WordSort.Params, searchPattern: String) {
        viewModelScope.launch {
            wordRepository.getAllBlacklist(sortParams, searchPattern)
                .catch { it.printStackTrace() }
                .collect { _words.postValue(it) }
        }
    }

    fun onSortWords(params: WordSort.Params, searchPattern: String) {
        when (indexTab) {
            TabWords.LANG.index -> onLoadAllWords(params, searchPattern)
            TabWords.FAVOURITES.index -> onLoadFavouritesWord(params, searchPattern)
            TabWords.BLACKLIST.index -> onLoadBlackListWord(params, searchPattern)
        }
    }

    companion object {
        private const val DEFAULT_SEARCH_PATTERN = ""
    }
}
package ua.notky.silfy.viewmodel.words

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.models.enums.TabWords
import ua.notky.silfy.models.model.Word
import ua.notky.silfy.models.observable.WordsModel
import ua.notky.silfy.tools.WordSort
import ua.notky.silfy.util.help.getTempAllWords
import ua.notky.silfy.util.help.getTempBlacklistWords
import ua.notky.silfy.util.help.getTempFavouritesWords

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

class WordsViewModel : BaseViewModel() {
    val model: WordsModel = WordsModel()
    private var indexTab: Int = TabWords.LANG.index

    private val _wordsLiveData: MutableLiveData<List<Word>> = MutableLiveData()
    val wordsLiveData: LiveData<List<Word>> = _wordsLiveData

    private fun updateWordList(value: List<Word>) {
        _wordsLiveData.postValue(value)
    }

    private fun updateWordList(
        value: List<Word>,
        sortParams: WordSort.Params,
        searchPattern: String
    ) {
        val words = value.filter {
            searchPattern.isEmpty() || (it.en.contains(searchPattern, true)
                    || it.ua.contains(searchPattern, true))
        }

        updateWordList(WordSort.sort(words, sortParams))
    }

    fun onSelectTab(index: Int, sortParams: WordSort.Params) {
        indexTab = index
        onRefreshWords(sortParams)
    }

    fun onRefreshWords(
        sortParams: WordSort.Params,
        searchPattern: String = DEFAULT_SEARCH_PATTERN
    ) {
        when (indexTab) {
            TabWords.LANG.index -> onLoadAllWords(sortParams, searchPattern)
            TabWords.FAVOURITES.index -> onLoadFavouritesWord(sortParams, searchPattern)
            TabWords.BLACKLIST.index -> onLoadBlackListWord(sortParams, searchPattern)
        }
    }

    private fun onLoadAllWords(sortParams: WordSort.Params, searchPattern: String) {
        updateWordList(getTempAllWords(), sortParams, searchPattern)
    }

    private fun onLoadFavouritesWord(sortParams: WordSort.Params, searchPattern: String) {
        updateWordList(getTempFavouritesWords(), sortParams, searchPattern)
    }

    private fun onLoadBlackListWord(sortParams: WordSort.Params, searchPattern: String) {
        updateWordList(getTempBlacklistWords(), sortParams, searchPattern)
    }

    fun onSortWords(params: WordSort.Params) {
        _wordsLiveData.value?.let {
            updateWordList(
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
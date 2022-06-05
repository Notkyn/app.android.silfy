package ua.notky.silfy.viewmodel.words

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.models.model.Word
import ua.notky.silfy.models.observable.WordsModel
import ua.notky.silfy.util.help.getTempAllWords
import ua.notky.silfy.util.help.getTempBlacklistWords
import ua.notky.silfy.util.help.getTempFavouritesWords
import ua.notky.silfy.tools.WordSort

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

class WordsViewModel : BaseViewModel() {
    val model: WordsModel = WordsModel()

    private val _wordsLiveData: MutableLiveData<List<Word>> = MutableLiveData()
    val wordsLiveData: LiveData<List<Word>> = _wordsLiveData

    private fun updateWordList(value: List<Word>) {
        _wordsLiveData.postValue(value)
    }

    fun clearSearch() {
        model.search.set("")
    }

    fun onLoadAllWords() {
        updateWordList(getTempAllWords())
    }

    fun onLoadFavouritesWord() {
        updateWordList(getTempFavouritesWords())
    }

    fun onLoadBlackListWord() {
        updateWordList(getTempBlacklistWords())
    }

    fun onSortWords(params: WordSort.Params) {
        _wordsLiveData.value?.let {
            updateWordList(WordSort.sort(
                it, params
            ))
        }
    }
}
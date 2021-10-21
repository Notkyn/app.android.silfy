package ua.notky.silfy.viewmodel.words

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.models.model.Word
import ua.notky.silfy.models.observable.WordsModel
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

    private val _wordsLiveData: MutableLiveData<List<Word>> = MutableLiveData()
    fun getWordsLiveData(): LiveData<List<Word>> {
        return _wordsLiveData
    }

    fun clearSearch() {
        model.search.set("")
    }

    fun onLoadAllWords() {
        _wordsLiveData.postValue(getTempAllWords())
    }

    fun onLoadFavouritesWord() {
        _wordsLiveData.postValue(getTempFavouritesWords())
    }

    fun onLoadBlackListWord() {
        _wordsLiveData.postValue(getTempBlacklistWords())
    }
}
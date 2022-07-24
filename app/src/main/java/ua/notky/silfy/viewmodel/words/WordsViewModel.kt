package ua.notky.silfy.viewmodel.words

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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

    val words: LiveData<List<Word>> = wordRepository.words

    fun isEmptyData() = words.value == null

    fun onSelectTab(index: Int, sortParams: WordSort.Params, searchPattern: String) {
        indexTab = index
        onRefreshWords(sortParams, searchPattern)
    }

    fun onRefreshWords(
        sortParams: WordSort.Params,
        searchPattern: String = DEFAULT_SEARCH_PATTERN
    ) {
        viewModelScope.launch {
            wordRepository.loadWords(indexTab, sortParams, searchPattern)
        }
    }

    fun onSortWords(params: WordSort.Params, searchPattern: String) {
        viewModelScope.launch {
            wordRepository.loadWords(indexTab, params, searchPattern)
        }
    }

    companion object {
        private const val DEFAULT_SEARCH_PATTERN = ""
    }
}
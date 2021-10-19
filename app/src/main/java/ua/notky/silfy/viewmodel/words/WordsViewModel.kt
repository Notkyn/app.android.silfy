package ua.notky.silfy.viewmodel.words

import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.model.observable.WordsModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

class WordsViewModel : BaseViewModel() {
    val model: WordsModel = WordsModel()

    fun clearSearch() {
        model.search.set("")
    }
}
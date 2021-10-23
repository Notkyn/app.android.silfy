package ua.notky.silfy.viewmodel.words

import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.models.model.Word
import ua.notky.silfy.models.observable.WordsModel
import ua.notky.silfy.models.states.WordState

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 23.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */


class WordsEditViewModel : BaseViewModel() {
    val model: WordsModel = WordsModel()

    fun selectWord(item: Word?) {
        clearModel()

        item?.let {
            model.id = item.id
            model.en.set(item.en)
            model.ru.set(item.ru)
            model.isBlacklist.set(item.isBlacklist)
            model.isFavourite.set(item.isFavourite)
            model.state.set(item.state)
        }
    }

    private fun clearModel() {
        model.id = null
        model.en.set("")
        model.ru.set("")
        model.isBlacklist.set(false)
        model.isFavourite.set(false)
        model.state.set(WordState.UNKNOWN)
    }

    fun isNewWord(): Boolean {
        return model.id == null
    }
}
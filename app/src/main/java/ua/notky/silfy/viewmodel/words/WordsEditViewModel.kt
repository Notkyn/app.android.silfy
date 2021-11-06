package ua.notky.silfy.viewmodel.words

import dagger.hilt.android.lifecycle.HiltViewModel
import ua.notky.base.changeable.ValidationMode
import ua.notky.base.util.log
import ua.notky.base.validation.ValidationModel
import ua.notky.base.validation.ValidationService
import ua.notky.base.viewmodel.BaseValidationViewModel
import ua.notky.silfy.config.ACTION_IS_SAVED
import ua.notky.silfy.models.model.Word
import ua.notky.silfy.models.observable.WordsModel
import ua.notky.silfy.models.states.WordState
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 23.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@HiltViewModel
class WordsEditViewModel @Inject constructor(
    override val validation: ValidationService
) : BaseValidationViewModel() {
    val model: WordsModel = WordsModel()

    fun selectWord(item: Word?) {
        clearModel()

        item?.let {
            model.id = item.id
            model.en.set(item.en)
            model.ua.set(item.ua)
            model.isBlacklist.set(item.isBlacklist)
            model.isFavourite.set(item.isFavourite)
            model.state.set(item.state)
        }
    }

    private fun clearModel() {
        model.id = null
        model.en.set("")
        model.ua.set("")
        model.isBlacklist.set(false)
        model.isFavourite.set(false)
        model.state.set(WordState.UNKNOWN)
    }

    fun isNewWord(): Boolean {
        return model.id == null
    }

    fun onChangeWordState() {
        when (model.state.get()) {
            WordState.UNKNOWN -> model.state.set(WordState.POOR)
            WordState.POOR -> model.state.set(WordState.AVERAGE)
            WordState.AVERAGE -> model.state.set(WordState.GOOD)
            WordState.GOOD -> model.state.set(WordState.EXCELLENT)
            WordState.EXCELLENT -> model.state.set(WordState.UNKNOWN)
        }
    }

    fun onSaveWord() {
        printModel()

        if(isValidWord()){
            setAction(ACTION_IS_SAVED)
        }
    }

    private fun isValidWord(): Boolean {
        return addValidateData(listOf(
            ValidationModel(ValidationMode.WORD_EU, model.en.get()),
            ValidationModel(ValidationMode.WORD_UA, model.ua.get())
        ))
    }

    @Deprecated("for test")
    private fun printModel() {
        this.log("printModel", "model", model.toString())
    }
}
package ua.notky.silfy.viewmodel.words

import dagger.hilt.android.lifecycle.HiltViewModel
import ua.notky.base.validation.ValidationModel
import ua.notky.base.validation.ValidationService
import ua.notky.base.viewmodel.BaseValidationViewModel
import ua.notky.silfy.config.ACTION_IS_DELETED
import ua.notky.silfy.config.ACTION_IS_SAVED
import ua.notky.silfy.config.VALIDATION_WORD_EU
import ua.notky.silfy.config.VALIDATION_WORD_UA
import ua.notky.silfy.models.model.Word
import ua.notky.silfy.models.observable.FormWordModel
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
    val wordModel = FormWordModel()
    val translateModel = FormWordModel()

    fun selectWord(item: Word?) {
        clearModel()

        item?.let {
            model.id = item.id
            wordModel.value.set(item.en)
            translateModel.value.set(item.ua)
            model.isBlacklist.set(item.isBlacklist)
            model.isFavourite.set(item.isFavourite)
            model.state.set(item.state)
        }
    }

    private fun clearModel() {
        model.id = null
        wordModel.value.set("")
        translateModel.value.set("")
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
            else -> model.state.set(WordState.POOR)
        }
    }

    fun onSaveWord() {
        if (isValidWord()) {
            setAction(ACTION_IS_SAVED)
        }
    }

    fun onDeleteWord() {
        setAction(ACTION_IS_DELETED)
    }

    private fun isValidWord(): Boolean {
        return addValidateData(
            listOf(
                ValidationModel(VALIDATION_WORD_EU, wordModel.value.get()),
                ValidationModel(VALIDATION_WORD_UA, translateModel.value.get())
            )
        )
    }
}
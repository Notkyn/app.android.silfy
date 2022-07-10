package ua.notky.silfy.models.observable

import androidx.databinding.ObservableField
import ua.notky.silfy.models.enums.AnswerType
import ua.notky.silfy.models.model.Word

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 10.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
data class WordAnswerModel(
    val type: ObservableField<AnswerType> = ObservableField(AnswerType.NORMAL),
    val word: Word
) {

    fun error() {
        type.set(AnswerType.ERROR)
    }

    fun success() {
        type.set(AnswerType.SUCCESS)
    }

    fun disable() {
        type.set(AnswerType.DISABLE)
    }

    fun checkWord(expect: Word? = null) {
        when {
            expect == null -> type.set(AnswerType.DISABLE)
            expect == word -> type.set(AnswerType.SUCCESS)
            expect != word -> type.set(AnswerType.ERROR)
        }
    }
}

package ua.notky.silfy.models.observable.answer

import androidx.databinding.ObservableField
import ua.notky.silfy.models.enums.GoLangType
import ua.notky.silfy.models.model.Word

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 10.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
data class WordAnswerSelectModel(
    val langType: ObservableField<GoLangType> = ObservableField(GoLangType.EN),
    val word: Word
) : BaseAnswerModel() {

    fun setType(type: GoLangType?) {
        langType.set(type)
    }

    override fun refresh() {
        val text = langType.get()?.let { word.getValueByType(it) } ?: ""
        answer.set(text)
    }
}

package ua.notky.silfy.models.observable.answer

import androidx.databinding.ObservableField
import ua.notky.silfy.models.enums.GoLangType

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 10.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class WordAnswerSymbolModel(
    val answer: ObservableField<String> = ObservableField(""),
    val langType: ObservableField<GoLangType> = ObservableField(GoLangType.EN)
) : BaseAnswerModel() {

    fun refresh() {
        answer.set("")
        normal()
    }
}
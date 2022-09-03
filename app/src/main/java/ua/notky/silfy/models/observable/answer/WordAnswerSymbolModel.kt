package ua.notky.silfy.models.observable.answer

import androidx.databinding.ObservableField
import ua.notky.silfy.models.enums.DifficultType
import ua.notky.silfy.models.enums.GoLangType

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 10.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class WordAnswerSymbolModel(
    val langType: ObservableField<GoLangType> = ObservableField(GoLangType.EN),
    private var difficult: DifficultType = DifficultType.EASY
) : BaseAnswerModel() {

    fun isEasyDifficult() = difficult == DifficultType.EASY

    fun setDifficult(type: DifficultType?) {
        type?.let { difficult = it }
    }
}
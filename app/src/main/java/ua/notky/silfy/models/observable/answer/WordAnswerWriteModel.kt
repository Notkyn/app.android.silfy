package ua.notky.silfy.models.observable.answer

import androidx.databinding.ObservableField

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 10.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

data class WordAnswerWriteModel(
    val answer: ObservableField<String> = ObservableField("")
): BaseAnswerModel() {

    fun refresh() {
        answer.set("")
        normal()
    }
}
package ua.notky.silfy.models.observable.answer

import androidx.databinding.ObservableField
import ua.notky.silfy.models.enums.AnswerType

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 10.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
open class BaseAnswerModel(
    val type: ObservableField<AnswerType> = ObservableField(AnswerType.NORMAL),
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

    fun normal() {
        type.set(AnswerType.NORMAL)
    }
}

package ua.notky.silfy.models.observable

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import ua.notky.silfy.models.enums.DifficultType
import ua.notky.silfy.models.enums.GoLangType
import ua.notky.silfy.models.enums.GoMode
import ua.notky.silfy.models.model.Word

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 08.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

data class GoModel(
    var difficult: DifficultType = DifficultType.EASY,
    var maxTime: Long = 0,
    var enableErrors: Boolean = false,
    val currentTime: ObservableField<Long> = ObservableField(0),
    val maxError: ObservableField<Int> = ObservableField(0),
    val currentError: ObservableField<Int> = ObservableField(0),
    val goMode: ObservableField<GoMode> = ObservableField(GoMode.SELECT.getRandomMode()),
    val word: ObservableField<Word> = ObservableField(),
    val nextClickable: ObservableBoolean = ObservableBoolean(true),
    val actualLangType: ObservableField<GoLangType> = ObservableField(GoLangType.EN),
    val expectLangType: ObservableField<GoLangType> = ObservableField(GoLangType.UA)
) {
    fun isTimeLeft(): Boolean {
        val time = currentTime.get() ?: 0
        return maxTime != 0L && time == 0L
    }
}
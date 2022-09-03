package ua.notky.silfy.models.observable

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import ua.notky.silfy.extension.parseToInt
import ua.notky.silfy.models.enums.DifficultType
import ua.notky.silfy.models.enums.GoLangType
import ua.notky.silfy.models.enums.GoMode
import ua.notky.silfy.models.model.Word
import kotlin.random.Random

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

    private fun isEasyDifficult() = difficult == DifficultType.EASY
    fun isHardDifficult() = difficult == DifficultType.HARD

    fun updateDifficult(type: DifficultType?) {
        type?.let { difficult = it }

        if (isEasyDifficult()) {
            // set mode via difficult
            goMode.set(GoMode.SELECT)
        }

        updateLangState()
    }

    fun updateLangState() {
        if (isEasyDifficult()) {
            actualLangType.set(GoLangType.EN)
            expectLangType.set(GoLangType.UA)
        } else {
            if (Random.nextBoolean()) {
                actualLangType.set(GoLangType.EN)
                expectLangType.set(GoLangType.UA)
            } else {
                actualLangType.set(GoLangType.UA)
                expectLangType.set(GoLangType.EN)
            }
        }
    }

    fun selectNextMode() {
        goMode.set(GoMode.SELECT.getRandomMode(isEasyDifficult()))
    }

    fun updateErrors(enable: Boolean?, value: String?) {
        val hasEnableErrors = enable ?: false
        val countErrors = value.parseToInt()

        val count = if (hasEnableErrors) {
            countErrors
        } else {
            ERRORS_EMPTY
        }

        enableErrors = hasEnableErrors
        maxError.set(count)
        currentError.set(ERRORS_EMPTY)
    }

    fun updateTimer(maxSeconds: Long?) {
        maxTime = maxSeconds ?: SECOND_EMPTY
        currentTime.set(maxTime)
    }

    fun isActivateTimer() = maxTime > SECOND_EMPTY

    fun hasNextTimerStep(): Boolean {
        val time = currentTime.get() ?: SECOND_EMPTY
        return if (time > SECOND_EMPTY) {
            currentTime.set(time.minus(STEP_ONE_SECOND))
            true
        } else {
            currentTime.set(time)
            false
        }
    }

    fun checkMaxErrors(isSuccess: Boolean): Boolean {
        return if (enableErrors && (maxError.get() ?: 0) > 0) {
            if (!isSuccess) {
                currentError.set(currentError.get()?.plus(1))
            }

            (currentError.get() ?: 0) >= (maxError.get() ?: 0)
        } else {
            false
        }
    }

    fun isSuccessAnswer(answer: String?): Boolean {
        return word.get()?.checkByType(answer, expectLangType.get()) ?: false
    }

    companion object {
        private const val STEP_ONE_SECOND = 1
        private const val SECOND_EMPTY = 0L
        private const val ERRORS_EMPTY = 0
    }
}
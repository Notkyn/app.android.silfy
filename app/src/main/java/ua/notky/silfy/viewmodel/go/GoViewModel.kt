package ua.notky.silfy.viewmodel.go

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.config.ACTION_TIME_LEFT
import ua.notky.silfy.models.enums.DifficultType
import ua.notky.silfy.models.enums.GoMode
import ua.notky.silfy.models.model.Word
import ua.notky.silfy.models.observable.GoModel
import ua.notky.silfy.ui.extension.parseToInt
import ua.notky.silfy.util.help.getTempAllWords
import kotlin.random.Random

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 08.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class GoViewModel : BaseViewModel() {
    val model = GoModel()
    val words: MutableList<Word> = mutableListOf()
    var isStarted: Boolean = false
    private var timerJob: Job? = null

    fun initializeDifficult(type: DifficultType?) {
        type?.let { model.difficult = it }

        if (model.difficult == DifficultType.EASY) {
            model.goMode.set(GoMode.SELECT)
        }
    }

    fun initializeTimer(value: Long?) {
        model.maxTime = value ?: SECOND_EMPTY
        model.currentTime.set(model.maxTime)

        if (model.maxTime > SECOND_EMPTY) {
            startTimer()
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            var timer = true

            while (timer) {
                delay(DELAY_ONE_SECOND)

                val time = model.currentTime.get() ?: SECOND_EMPTY
                val result = if (time > SECOND_EMPTY) {
                    time.minus(STEP_ONE_SECOND)
                } else {
                    timer = false
                    time
                }

                model.currentTime.set(result)

                if (model.isTimeLeft()) {
                    setAction(ACTION_TIME_LEFT)
                    timerJob?.cancel()
                }
            }
        }
    }

    fun initializeErrors(enable: Boolean?, value: String?) {
        val enableErrors = enable ?: false
        val countErrors = value.parseToInt()

        val count = if (enableErrors) {
            countErrors
        } else {
            ERRORS_EMPTY
        }

        model.maxError.set(count)
        model.currentError.set(ERRORS_EMPTY)
    }

    fun initializeWords() {
        words.addAll(getTempAllWords())
        words.shuffle()
        selectNextWord()
    }

    fun onNext() {
        selectNextMode()
        selectNextWord()
    }

    private fun selectNextMode() {
        if (model.difficult != DifficultType.EASY) {
            model.goMode.set(GoMode.SELECT.getRandomMode())
        }
    }

    private fun selectNextWord() {
        model.word.set(words[Random.nextInt(words.size)])
    }

    companion object {
        private const val DELAY_ONE_SECOND = 1000L
        private const val STEP_ONE_SECOND = 1
        private const val SECOND_EMPTY = 0L
        private const val ERRORS_EMPTY = 0
    }
}
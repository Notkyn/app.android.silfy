package ua.notky.silfy.viewmodel.go

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.models.enums.GoMode
import ua.notky.silfy.models.observable.GoModel
import ua.notky.silfy.ui.extension.parseToInt

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 08.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class GoViewModel : BaseViewModel() {
    val model = GoModel()

    private var timerJob: Job? = null

    fun initializeTimer(value: Long?) {
        model.maxTime = value ?: SECOND_EMPTY
        model.currentTime.set(model.maxTime)

        if (model.maxTime > SECOND_EMPTY) {
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
                }
            }
        }
    }

    fun initializeErrors(enable: Boolean?, value: String?) {
        val enableErrors = enable ?: false
        val countErrors = value.parseToInt()

        val count = if(enableErrors) {
            countErrors
        } else {
            ERRORS_EMPTY
        }

        model.maxError.set(count)
        model.currentError.set(ERRORS_EMPTY)
    }

    fun onNext() {
        model.goMode.set(GoMode.SELECT.getRandomMode())
    }

    companion object {
        private const val DELAY_ONE_SECOND = 1000L
        private const val STEP_ONE_SECOND = 1
        private const val SECOND_EMPTY = 0L
        private const val ERRORS_EMPTY = 0
    }
}
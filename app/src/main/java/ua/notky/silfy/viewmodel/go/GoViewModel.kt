package ua.notky.silfy.viewmodel.go

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.config.ACTION_EMPTY_WORDS
import ua.notky.silfy.config.ACTION_MAX_ERRORS
import ua.notky.silfy.config.ACTION_TIME_LEFT
import ua.notky.silfy.models.enums.DifficultType
import ua.notky.silfy.models.enums.GoLangType
import ua.notky.silfy.models.enums.GoMode
import ua.notky.silfy.models.model.Word
import ua.notky.silfy.models.observable.GoModel
import ua.notky.silfy.models.observable.WordAnswerModel
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

    private val _answerWords: MutableLiveData<List<WordAnswerModel>> = MutableLiveData()
    val answerWords: LiveData<List<WordAnswerModel>> = _answerWords

    fun initializeDifficult(type: DifficultType?) {
        type?.let { model.difficult = it }

        if (model.difficult == DifficultType.EASY) {
            model.goMode.set(GoMode.SELECT)
        }

        selectLangType()
    }

    private fun selectLangType() {
        if (model.difficult == DifficultType.EASY) {
            model.actualLangType.set(GoLangType.EN)
            model.expectLangType.set(GoLangType.UA)
        } else {
            val state = Random.nextBoolean()
            if (state) {
                model.actualLangType.set(GoLangType.EN)
                model.expectLangType.set(GoLangType.UA)
            } else {
                model.actualLangType.set(GoLangType.UA)
                model.expectLangType.set(GoLangType.EN)
            }
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

        model.enableErrors = enableErrors
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
        selectLangType()
        selectNextWord()
        model.nextClickable.set(true)
    }

    private fun selectNextMode() {
        if (model.difficult != DifficultType.EASY) {
            model.goMode.set(GoMode.SELECT.getRandomMode())
        }
    }

    private fun selectNextWord() {
        if (words.isNotEmpty()) {
            val word = words[Random.nextInt(0, words.size)]
            model.word.set(word)

            when (model.goMode.get()) {
                GoMode.SELECT -> prepareSelectMode(word)
                else -> {}
            }
        } else {
            setAction(ACTION_EMPTY_WORDS)
        }
    }

    private fun prepareSelectMode(word: Word) {
        if (words.size > COUNT_SELECT_ANSWER_WORDS) {
            val listWords: MutableList<Word> = mutableListOf()
            listWords.add(word)

            do {
                val tempWord = words[Random.nextInt(words.size)]
                if (!listWords.contains(tempWord)) {
                    listWords.add(tempWord)
                }
            } while (listWords.size < COUNT_SELECT_ANSWER_WORDS)

            setAnswerWords(listWords)
        } else {
            setAnswerWords(words)
        }
    }

    private fun setAnswerWords(words: List<Word>) {
        val list: MutableList<WordAnswerModel> = mutableListOf()
        list.addAll(words.map { WordAnswerModel(word = it).apply { this.setType(model.expectLangType.get()) } })
        list.shuffle()
        _answerWords.postValue(list)
    }

    fun onCheckResult(word: Word) {
        blockMovingToNext()
        when (model.goMode.get()) {
            GoMode.SELECT -> checkSelectResult(word)
            else -> {}
        }
        checkError(word)
    }

    private fun blockMovingToNext() {
        model.nextClickable.set(false)

        viewModelScope.launch {
            delay(DELAY_NEXT_STEP)
            onNext()
        }
    }

    private fun checkSelectResult(word: Word) {
        _answerWords.value?.forEach {
            when {
                it.word == word && it.word == model.word.get() -> it.success()
                it.word == word && it.word != model.word.get() -> it.error()
                it.word != word && it.word == model.word.get() -> it.success()
                else -> it.disable()
            }
        }
    }

    private fun checkError(expect: Word) {
        if (model.enableErrors && (model.maxError.get() ?: 0) > 0) {
            val isSuccess = model.word.get() == expect

            if (!isSuccess) {
                model.currentError.set(model.currentError.get()?.plus(1))
            }

            if ((model.currentError.get() ?: 0) >= (model.maxError.get() ?: 0)) {
                setAction(ACTION_MAX_ERRORS)
            }
        }
    }

    companion object {
        private const val DELAY_ONE_SECOND = 1000L
        private const val DELAY_NEXT_STEP = 2000L
        private const val STEP_ONE_SECOND = 1
        private const val SECOND_EMPTY = 0L
        private const val ERRORS_EMPTY = 0
        private const val COUNT_SELECT_ANSWER_WORDS = 5
    }
}
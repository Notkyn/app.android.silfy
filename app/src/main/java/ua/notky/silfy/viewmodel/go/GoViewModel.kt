package ua.notky.silfy.viewmodel.go

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.config.ACTION_EMPTY_WORDS
import ua.notky.silfy.config.ACTION_MAX_ERRORS
import ua.notky.silfy.config.ACTION_TIME_LEFT
import ua.notky.silfy.models.enums.*
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.models.model.Word
import ua.notky.silfy.models.observable.GoModel
import ua.notky.silfy.models.observable.GoStatsModel
import ua.notky.silfy.models.observable.answer.WordAnswerSelectModel
import ua.notky.silfy.models.observable.answer.WordAnswerSymbolModel
import ua.notky.silfy.models.observable.answer.WordAnswerWriteModel
import ua.notky.silfy.models.states.FetchSessionResult
import ua.notky.silfy.models.states.GoUiState
import ua.notky.silfy.extension.parseToInt
import ua.notky.silfy.usecase.go.FetchStartSessionUseCase
import ua.notky.silfy.usecase.go.FinishSessionStatsUseCase
import ua.notky.silfy.usecase.go.UpdateSessionStatsUseCase
import javax.inject.Inject
import kotlin.random.Random

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 08.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@HiltViewModel
class GoViewModel @Inject constructor(
    private val fetchStartSessionUseCase: FetchStartSessionUseCase,
    private val updateSessionStatsUseCase: UpdateSessionStatsUseCase,
    private val finishSessionStatsUseCase: FinishSessionStatsUseCase
) : BaseViewModel() {
    val model = GoModel()
    val stats = GoStatsModel()
    val writeAnswerModel = WordAnswerWriteModel()
    val symbolAnswerModel = WordAnswerSymbolModel()
    val words: MutableList<Word> = mutableListOf()
    var isStarted: Boolean = false
    private var timerJob: Job? = null

    private val _answerWords: MutableLiveData<List<WordAnswerSelectModel>> = MutableLiveData()
    val answerWords: LiveData<List<WordAnswerSelectModel>> = _answerWords

    private val _answerSymbolWord: MutableLiveData<Word> = MutableLiveData()
    val answerSymbolWord: LiveData<Word> = _answerSymbolWord

    private val _uiState: MutableLiveData<GoUiState> = MutableLiveData()
    val uiState: LiveData<GoUiState> = _uiState

    fun clearState() {
        _uiState.postValue(GoUiState.Normal)
    }

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
                delay(DELAY_ONE_SECOND_FOR_TIMER)

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

    fun initializeWords(
        wordsType: SelectedWordsType?,
        isBlacklist: Boolean,
        categories: List<Category>?
    ) {
        viewModelScope.launch {
            _uiState.postValue(GoUiState.Loading)

            val params = FetchStartSessionUseCase.Params(
                wordsType, isBlacklist, categories
            )

            when (val result = fetchStartSessionUseCase.fetch(params)) {
                is FetchSessionResult.Success -> {
                    stats.sessionId = result.session.id
                    words.clear()
                    words.addAll(result.words)
                    words.shuffle()
                    stats.totalCountWords = words.size
                    _uiState.postValue(GoUiState.Loaded)
                }
                is FetchSessionResult.Failure -> _uiState.postValue(GoUiState.Failure)
            }
        }
    }

    fun start() {
        selectNextWord()
    }

    fun onNext() {
        model.nextClickable.set(false)
        prepareNextStage()
    }

    private fun prepareNextStage() {
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
            stats.addUsedWord(word)

            when (model.goMode.get()) {
                GoMode.SELECT -> prepareSelectMode(word)
                GoMode.WRITE -> prepareWriteMode()
                GoMode.SYMBOL -> prepareSymbolMode()
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
        val list: MutableList<WordAnswerSelectModel> = mutableListOf()
        list.addAll(words.map { WordAnswerSelectModel(word = it).apply { this.setType(model.expectLangType.get()) } })
        list.shuffle()
        _answerWords.postValue(list)
    }

    private fun prepareWriteMode() {
        writeAnswerModel.refresh()
    }

    private fun prepareSymbolMode() {
        symbolAnswerModel.refresh()
        symbolAnswerModel.langType.set(model.expectLangType.get())
        _answerSymbolWord.postValue(model.word.get()?.copy())
    }

    fun onCheckResult(selectWord: WordAnswerSelectModel? = null) {
        model.nextClickable.set(false)

        viewModelScope.launch {
            val isSuccess = when (model.goMode.get()) {
                GoMode.SELECT -> selectWord?.let { checkAnswerResultBySelect(it) } ?: false
                GoMode.WRITE -> checkAnswerResultByWrite()
                GoMode.SYMBOL -> checkAnswerResultBySymbol()
                else -> false
            }

            checkError(isSuccess)
        }
    }

    private fun checkAnswerResultBySelect(word: WordAnswerSelectModel): Boolean {
        val isSuccess = model.word.get()?.checkByType(
            word.answer.get(),
            model.expectLangType.get()
        ) ?: false

        _answerWords.value?.forEach { it.disable() }

        when (isSuccess) {
            true -> word.success()
            false -> {
                word.error()
                setErrorAnswerForSelectMode()
            }
        }

        return isSuccess
    }

    private fun setErrorAnswerForSelectMode() {
        val answers = _answerWords.value?.filter {
            model.word.get()?.checkByType(
                it.answer.get(),
                model.expectLangType.get()
            ) ?: false
        }

        val result = if (!answers.isNullOrEmpty()) {
            if (answers.size == 1) {
                answers.first()
            } else {
                answers[Random.nextInt(0, answers.size)]
            }
        } else {
            null
        }

        result?.success()

//        result?.let {
//            if (isSuccess) {
//                result.success()
//            } else {
//                result.error()
//            }
//        }
    }

    private fun checkAnswerResultByWrite(): Boolean {
        val answer = writeAnswerModel.answer.get()

        val isSuccess = model.word.get()?.checkByType(answer, model.expectLangType.get()) ?: false

        when (isSuccess) {
            true -> writeAnswerModel.success()
            false -> writeAnswerModel.error()
        }

        return isSuccess
    }

    private fun checkAnswerResultBySymbol(): Boolean {
        val answer = symbolAnswerModel.answer.get()

        val isSuccess = model.word.get()?.checkByType(answer, model.expectLangType.get()) ?: false

        when (isSuccess) {
            true -> symbolAnswerModel.success()
            false -> symbolAnswerModel.error()
        }

        return isSuccess
    }

    private suspend fun checkError(isSuccess: Boolean) {
        stats.addSuccess(isSuccess)
        updateTrainingSession(isSuccess)

        if (model.enableErrors && (model.maxError.get() ?: 0) > 0) {
            if (!isSuccess) {
                model.currentError.set(model.currentError.get()?.plus(1))
            }

            if ((model.currentError.get() ?: 0) >= (model.maxError.get() ?: 0)) {
                setAction(ACTION_MAX_ERRORS)
            }
        }

        delay(DELAY_NEXT_STEP)
        prepareNextStage()
    }

    private fun updateTrainingSession(isSuccess: Boolean) {
        viewModelScope.launch {
            val params = UpdateSessionStatsUseCase.Params(
                stats.sessionId,
                stats.usedWord?.id,
                isSuccess
            )

            updateSessionStatsUseCase.update(params)
        }
    }

    fun onFinishTrainingSession(reason: GoStatsType) {
        viewModelScope.launch {
            val params = FinishSessionStatsUseCase.Params(
                stats.sessionId,
                reason.id
            )

            finishSessionStatsUseCase.update(params)
        }
    }

    fun onAddSymbolAnswer(symbol: String) {
        val value = symbolAnswerModel.answer.get() ?: ""
        val result = value.plus(symbol)

        symbolAnswerModel.answer.set(result)
    }

    fun onDeleteSymbolAnswer(symbol: String) {
        val value = symbolAnswerModel.answer.get() ?: ""

        if (value.isEmpty()) return

        val indexLastChar = value.lastIndexOf(symbol)
        val result = value.filterIndexed { index, _ ->
            index != indexLastChar
        }

        symbolAnswerModel.answer.set(result)
    }

    companion object {
        private const val DELAY_ONE_SECOND_FOR_TIMER = 1000L
        private const val DELAY_NEXT_STEP = 1000L
        private const val STEP_ONE_SECOND = 1
        private const val SECOND_EMPTY = 0L
        private const val ERRORS_EMPTY = 0
        private const val COUNT_SELECT_ANSWER_WORDS = 5
    }
}
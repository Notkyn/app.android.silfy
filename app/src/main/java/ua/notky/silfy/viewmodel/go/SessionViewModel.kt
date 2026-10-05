package ua.notky.silfy.viewmodel.go

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.models.enums.GoStatsType
import ua.notky.silfy.models.model.Profile
import ua.notky.silfy.models.states.FetchSessionResult
import ua.notky.silfy.repository.db.dao.ProfileDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import ua.notky.silfy.session.SessionEngine
import ua.notky.silfy.session.SessionQuestion
import ua.notky.silfy.ui.view.level.level
import ua.notky.silfy.usecase.go.FetchStartSessionUseCase
import ua.notky.silfy.usecase.go.FinishSessionStatsUseCase
import ua.notky.silfy.usecase.go.UpdateSessionStatsUseCase
import ua.notky.silfy.usecase.settings.SessionSettingsUseCase
import javax.inject.Inject

/**
 * 4c–4j Training session. Activity-scoped (GoActivity): the session goes on from the session screen
 * to the results and back ("Another session"). The timer stands while [pause] has any reason.
 */
@HiltViewModel
class SessionViewModel @Inject constructor(
    private val dataStore: AppDataStorePreferences,
    private val profileDao: ProfileDao,
    private val settingsUseCase: SessionSettingsUseCase,
    private val fetchStartSessionUseCase: FetchStartSessionUseCase,
    private val updateSessionStatsUseCase: UpdateSessionStatsUseCase,
    private val finishSessionStatsUseCase: FinishSessionStatsUseCase
) : BaseViewModel() {

    sealed class Step {
        object Loading : Step()
        object Running : Step()
        data class Finished(val result: Result) : Step()
        /** No word matches the settings any more */
        object Empty : Step()
        object Failure : Step()
    }

    /** What the session screen shows */
    data class State(
        val question: SessionQuestion,
        val number: Int,
        val mistakes: Int,
        /** null — no mistake limit */
        val maxMistakes: Int?,
        /** "UA", "PL"… — the direction badge */
        val languageBadge: String,
        /** Choose: the tapped option */
        val picked: String? = null,
        /** Letters: indices of [SessionQuestion.Letters.tiles] in the order they were tapped */
        val usedTiles: List<Int> = emptyList(),
        /** Type: the text in the field */
        val typed: String = "",
        /** null until the answer is checked */
        val feedback: Feedback? = null
    ) {
        val builtWord: String
            get() = (question as? SessionQuestion.Letters)?.let { q -> usedTiles.joinToString("") { q.tiles[it] } }.orEmpty()

        /** Check is possible: Letters — every tile of the answer is placed, Type — something is typed */
        val canCheck: Boolean
            get() = feedback == null && when (val q = question) {
                is SessionQuestion.Choose -> false
                is SessionQuestion.Letters -> usedTiles.size >= q.answer.length
                is SessionQuestion.Type -> typed.isNotBlank()
            }
    }

    data class Feedback(val isCorrect: Boolean, val isLevelUp: Boolean)

    data class Result(
        val reason: GoStatsType,
        val profileName: String,
        val selected: Int,
        val used: Int,
        val correct: Int,
        val wrong: Int
    ) {
        val accuracy: Int get() = if (correct + wrong == 0) 0 else Math.round(correct * 100f / (correct + wrong))
    }

    enum class PauseReason { DIALOG, BACKGROUND }

    private val _step = MutableLiveData<Step>()
    val step: LiveData<Step> = _step

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    /** Separate from [state]: a tick does not redraw the question */
    private val _secondsLeft = MutableLiveData<Int>()
    val secondsLeft: LiveData<Int> = _secondsLeft

    private var engine: SessionEngine? = null
    private var profile: Profile? = null
    private var sessionId: String? = null
    private var correct = 0
    private var wrong = 0
    private val usedWordIds = mutableSetOf<Int>()

    private var timerJob: Job? = null
    private val pauseReasons = mutableSetOf<PauseReason>()

    /** An answer is being saved: taps are ignored */
    private var isAnswering = false

    /** A new session with the saved settings */
    fun start() {
        if (_step.value == Step.Loading) return
        stopTimer()
        _step.value = Step.Loading

        viewModelScope.launch {
            val settings = settingsUseCase.load().getOrNull()
            val profileId = dataStore.getProfileId()
            profile = profileId?.let { profileDao.getById(it) }

            if (settings == null || profile == null) {
                _step.value = Step.Failure
                return@launch
            }

            when (val result = fetchStartSessionUseCase.fetch(settings)) {
                is FetchSessionResult.Success -> {
                    engine = SessionEngine(result.words, result.dictionary, settings.isHard)
                    sessionId = result.session.id
                    correct = 0
                    wrong = 0
                    usedWordIds.clear()

                    _secondsLeft.value = settings.minutes * SECONDS_IN_MINUTE
                    _state.value = newQuestionState(
                        number = 1,
                        mistakes = 0,
                        maxMistakes = if (settings.isMistakeLimit) settings.maxMistakes else null
                    )
                    _step.value = Step.Running
                    startTimer()
                }
                FetchSessionResult.Empty -> _step.value = Step.Empty
                is FetchSessionResult.Failure -> _step.value = Step.Failure
            }
        }
    }

    fun choose(option: String) {
        val state = _state.value ?: return
        if (state.question !is SessionQuestion.Choose || state.feedback != null) return
        _state.value = state.copy(picked = option)
        answer(option)
    }

    fun tapTile(index: Int) {
        val state = _state.value ?: return
        val question = state.question as? SessionQuestion.Letters ?: return
        if (state.feedback != null || index in state.usedTiles || state.usedTiles.size >= question.answer.length) return
        _state.value = state.copy(usedTiles = state.usedTiles + index)
    }

    fun eraseTile() {
        val state = _state.value ?: return
        if (state.feedback != null || state.usedTiles.isEmpty()) return
        _state.value = state.copy(usedTiles = state.usedTiles.dropLast(1))
    }

    fun setTyped(value: String) {
        val state = _state.value ?: return
        if (state.feedback != null || state.typed == value) return
        _state.value = state.copy(typed = value)
    }

    /** Letters / Type */
    fun check() {
        val state = _state.value ?: return
        if (!state.canCheck) return
        when (state.question) {
            is SessionQuestion.Letters -> answer(state.builtWord)
            is SessionQuestion.Type -> answer(state.typed)
            is SessionQuestion.Choose -> {}
        }
    }

    /** After the feedback: the mistake limit ends the session, otherwise the next question */
    fun next() {
        val state = _state.value ?: return
        if (state.feedback == null || _step.value != Step.Running) return

        if (state.maxMistakes != null && state.mistakes >= state.maxMistakes) {
            finish(GoStatsType.ERROR)
            return
        }

        _state.value = newQuestionState(state.number + 1, state.mistakes, state.maxMistakes)
    }

    /** ✕ / Back → "End session" */
    fun end() {
        if (_step.value == Step.Running) finish(GoStatsType.OTHER)
    }

    fun pause(reason: PauseReason) {
        pauseReasons += reason
        stopTimer()
    }

    fun resume(reason: PauseReason) {
        pauseReasons -= reason
        startTimer()
    }

    private fun answer(value: String) {
        val state = _state.value ?: return
        val engine = engine ?: return
        if (isAnswering) return
        isAnswering = true

        val question = state.question
        val isCorrect = engine.isCorrect(question, value)
        if (isCorrect) correct++ else wrong++

        viewModelScope.launch {
            val params = UpdateSessionStatsUseCase.Params(sessionId, question.word.id, isCorrect)
            val newState = updateSessionStatsUseCase.update(params).getOrNull()

            val isLevelUp = newState != null && newState.level > question.word.state.level
            newState?.let { engine.updateWord(question.word.copy(state = it)) }

            isAnswering = false
            val current = _state.value ?: return@launch
            // The time may have run out meanwhile
            if (_step.value != Step.Running || current.question != question) return@launch
            _state.value = current.copy(
                feedback = Feedback(isCorrect, isCorrect && isLevelUp),
                mistakes = if (isCorrect) current.mistakes else current.mistakes + 1
            )
        }
    }

    /** "Words used" counts every word shown, answered or not */
    private fun newQuestionState(number: Int, mistakes: Int, maxMistakes: Int?): State {
        val question = requireNotNull(engine).next()
        question.word.id?.let { usedWordIds += it }

        return State(
            question = question,
            number = number,
            mistakes = mistakes,
            maxMistakes = maxMistakes,
            languageBadge = profile?.language?.badge.orEmpty()
        )
    }

    private fun startTimer() {
        if (timerJob?.isActive == true || pauseReasons.isNotEmpty() || _step.value != Step.Running) return

        timerJob = viewModelScope.launch {
            while (isActive) {
                delay(ONE_SECOND)
                val left = (_secondsLeft.value ?: 0) - 1
                _secondsLeft.value = left.coerceAtLeast(0)
                if (left <= 0) {
                    finish(GoStatsType.TIME)
                    return@launch
                }
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    private fun finish(reason: GoStatsType) {
        stopTimer()
        val engine = engine ?: return

        _step.value = Step.Finished(
            Result(
                reason = reason,
                profileName = profile?.name.orEmpty(),
                selected = engine.poolSize,
                used = usedWordIds.size,
                correct = correct,
                wrong = wrong
            )
        )

        viewModelScope.launch {
            finishSessionStatsUseCase.update(FinishSessionStatsUseCase.Params(sessionId, reason.id))
        }
    }

    private companion object {
        const val ONE_SECOND = 1000L
        const val SECONDS_IN_MINUTE = 60
    }
}

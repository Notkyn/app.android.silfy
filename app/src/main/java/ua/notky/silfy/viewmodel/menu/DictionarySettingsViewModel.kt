package ua.notky.silfy.viewmodel.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.models.model.WordCounts
import ua.notky.silfy.repository.db.dao.word.WordListDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import ua.notky.silfy.usecase.dictionary.ClearAllBlacksUseCase
import ua.notky.silfy.usecase.dictionary.ClearAllFavouritesUseCase
import ua.notky.silfy.usecase.dictionary.ClearLearningProgressUseCase
import ua.notky.silfy.usecase.dictionary.ResetDefaultWordsUseCase
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 29.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

/**
 * 6c Dictionary: live counters, the 4 actions behind the 6d dialogs. The success banner of the last action
 * stays until the next action or until the screen is left.
 */
@HiltViewModel
class DictionarySettingsViewModel @Inject constructor(
    private val dataStore: AppDataStorePreferences,
    private val wordListDao: WordListDao,
    private val clearAllBlacksUseCase: ClearAllBlacksUseCase,
    private val clearAllFavouritesUseCase: ClearAllFavouritesUseCase,
    private val clearLearningProgressUseCase: ClearLearningProgressUseCase,
    private val resetDefaultWordsUseCase: ResetDefaultWordsUseCase
) : BaseViewModel() {

    enum class Action { RESET_PROGRESS, DEFAULT_SET, CLEAR_FAVOURITES, CLEAR_BLACKLIST }

    sealed class State {
        object Idle : State()
        object Working : State()
        data class Done(val action: Action) : State()
        object Failure : State()
    }

    private val profileId = MutableLiveData<Int>()

    val counts: LiveData<WordCounts> = profileId.switchMap { wordListDao.getCounts(it) }

    private val _state = MutableLiveData<State>(State.Idle)
    val state: LiveData<State> = _state

    init {
        viewModelScope.launch {
            dataStore.getProfileId()?.let { profileId.value = it }
        }
    }

    fun run(action: Action) {
        if (_state.value == State.Working) return

        _state.value = State.Working
        viewModelScope.launch {
            val result = when (action) {
                Action.RESET_PROGRESS -> clearLearningProgressUseCase.clear()
                Action.DEFAULT_SET -> resetDefaultWordsUseCase.reset()
                Action.CLEAR_FAVOURITES -> clearAllFavouritesUseCase.clear()
                Action.CLEAR_BLACKLIST -> clearAllBlacksUseCase.clear()
            }
            _state.value = if (result.isSuccess) State.Done(action) else State.Failure
        }
    }

    /** The failure dialog is shown once */
    fun consumeFailure() {
        if (_state.value == State.Failure) _state.value = State.Idle
    }
}

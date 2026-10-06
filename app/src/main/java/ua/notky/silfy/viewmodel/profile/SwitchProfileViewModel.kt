package ua.notky.silfy.viewmodel.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.models.model.Profile
import ua.notky.silfy.repository.db.dao.ProfileDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import ua.notky.silfy.usecase.profile.ActiveProfileUseCase
import javax.inject.Inject

/**
 * 5c Switch profile sheet. Scoped to the sheet: survives the activity recreation caused by switching
 * the app language to the language of the new profile.
 */
@HiltViewModel
class SwitchProfileViewModel @Inject constructor(
    profileDao: ProfileDao,
    private val dataStore: AppDataStorePreferences,
    private val activeProfileUseCase: ActiveProfileUseCase
) : BaseViewModel() {

    enum class State { IDLE, SWITCHING, SWITCHED, FAILURE }

    val profiles: LiveData<List<Profile>> = profileDao.getAll()

    private val _activeId = MutableLiveData<Int>()
    val activeId: LiveData<Int> = _activeId

    private val _state = MutableLiveData(State.IDLE)
    val state: LiveData<State> = _state

    init {
        viewModelScope.launch { dataStore.getProfileId()?.let { _activeId.value = it } }
    }

    fun switch(profile: Profile) {
        if (_state.value != State.IDLE || profile.id == _activeId.value) return

        _state.value = State.SWITCHING
        viewModelScope.launch {
            _state.value = try {
                activeProfileUseCase.set(profile)
                State.SWITCHED
            } catch (ex: Exception) {
                ex.printStackTrace()
                State.FAILURE
            }
        }
    }

    /** Navigation events and failures are handled once */
    fun consumeState() {
        _state.value = State.IDLE
    }
}

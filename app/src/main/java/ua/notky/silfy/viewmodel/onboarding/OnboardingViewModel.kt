package ua.notky.silfy.viewmodel.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.notky.base.model.ResultState
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.models.enums.AppLanguage
import ua.notky.silfy.models.model.Profile
import ua.notky.silfy.models.states.OnboardingUiState
import ua.notky.silfy.repository.db.dao.ProfileDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import ua.notky.silfy.usecase.SaveDefaultDataUseCase
import ua.notky.silfy.usecase.profile.ActiveProfileUseCase
import ua.notky.silfy.usecase.profile.CreateProfileUseCase
import javax.inject.Inject

/**
 * Welcome, Who's learning and Create profile. Scoped to AuthActivity: survives the activity
 * recreation caused by switching the app language to the profile language.
 */
@HiltViewModel
class OnboardingViewModel @Inject constructor(
    profileDao: ProfileDao,
    private val dataStore: AppDataStorePreferences,
    private val createProfileUseCase: CreateProfileUseCase,
    private val activeProfileUseCase: ActiveProfileUseCase,
    private val defaultDataUseCase: SaveDefaultDataUseCase
) : BaseViewModel() {

    val profiles: LiveData<List<Profile>> = profileDao.getAll()

    private val _state: MutableLiveData<OnboardingUiState> = MutableLiveData(OnboardingUiState.Idle)
    val state: LiveData<OnboardingUiState> = _state

    fun onWelcomeShown() {
        viewModelScope.launch { dataStore.setWelcomeShown() }
    }

    fun selectProfile(profile: Profile) {
        viewModelScope.launch {
            activeProfileUseCase.set(profile)
            _state.value = OnboardingUiState.ProfileSelected
        }
    }

    fun createProfile(name: String, language: AppLanguage) {
        if (_state.value == OnboardingUiState.Creating) return
        _state.value = OnboardingUiState.Creating

        viewModelScope.launch {
            val params = CreateProfileUseCase.Params(name, language)

            when (val result = createProfileUseCase.create(params)) {
                is ResultState.Success.Result -> onProfileCreated(result.data)
                else -> _state.value = OnboardingUiState.Failure
            }
        }
    }

    private suspend fun onProfileCreated(profile: Profile) {
        // Switches the app language too: AuthActivity is recreated, this view model is not
        activeProfileUseCase.set(profile)
        defaultDataUseCase.fetch()
        _state.value = OnboardingUiState.ProfileCreated(profile.language)
    }

    /** Navigation events are handled once */
    fun consumeState() {
        _state.value = OnboardingUiState.Idle
    }
}

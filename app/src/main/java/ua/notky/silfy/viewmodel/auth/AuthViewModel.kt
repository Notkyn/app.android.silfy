package ua.notky.silfy.viewmodel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.notky.base.model.ResultState
import ua.notky.base.validation.ValidationModel
import ua.notky.base.validation.ValidationService
import ua.notky.base.viewmodel.BaseValidationViewModel
import ua.notky.silfy.config.VALIDATION_PROFILE_NAME
import ua.notky.silfy.models.enums.AppLanguage
import ua.notky.silfy.models.model.Profile
import ua.notky.silfy.models.observable.AuthModel
import ua.notky.silfy.models.states.AuthUiState
import ua.notky.silfy.repository.db.dao.ProfileDao
import ua.notky.silfy.usecase.SaveDefaultDataUseCase
import ua.notky.silfy.usecase.profile.ActiveProfileUseCase
import ua.notky.silfy.usecase.profile.CreateProfileUseCase
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@HiltViewModel
class AuthViewModel @Inject constructor(
    profileDao: ProfileDao,
    override val validation: ValidationService,
    private val activeProfileUseCase: ActiveProfileUseCase,
    private val createProfileUseCase: CreateProfileUseCase,
    private val defaultDataUseCase: SaveDefaultDataUseCase,
) : BaseValidationViewModel() {
    private val model: AuthModel = AuthModel()

    private val _profileState: MutableLiveData<AuthUiState> = MutableLiveData()
    val profileState: LiveData<AuthUiState> = _profileState

    val profiles: LiveData<List<Profile>> = profileDao.getAll()

    fun getEmptyModel(): AuthModel {
        model.name.set("")
        return model
    }

    fun onContinue() {
        if (isValidName()) {
            onCreateProfile()
        }
    }

    fun onContinue(profile: Profile) {
        viewModelScope.launch {
            _profileState.postValue(AuthUiState.Loading)
            if (profile.id != null) {
                activeProfileUseCase.set(profile)
                _profileState.postValue(AuthUiState.Loaded)
            } else {
                _profileState.postValue(AuthUiState.Failure.Missing)
            }
        }
    }

    private fun onCreateProfile() {
        viewModelScope.launch {
            _profileState.postValue(AuthUiState.Loading)

            // Until the new onboarding (step 5) there is no language picker
            val params = CreateProfileUseCase.Params(model.name.get(), AppLanguage.UK)

            when (val result = createProfileUseCase.create(params)) {
                is ResultState.Success.Result -> handleLoadedProfile(result.data, true)
                ResultState.Success.Empty -> _profileState.postValue(AuthUiState.Failure.Missing)
                is ResultState.Failure -> _profileState.postValue(AuthUiState.Failure.ErrorCreate)
            }
        }
    }

    private suspend fun handleLoadedProfile(profile: Profile, isCreated: Boolean = false) {
        if (profile.id != null) {
            activeProfileUseCase.set(profile)

            if (isCreated) {
                defaultDataUseCase.fetch()
                _profileState.postValue(AuthUiState.Created)
            } else {
                _profileState.postValue(AuthUiState.Loaded)
            }
        } else {
            _profileState.postValue(AuthUiState.Failure.Missing)
        }
    }

    private fun isValidName(): Boolean {
        return addValidateData(
            listOf(
                ValidationModel(VALIDATION_PROFILE_NAME, model.name.get())
            )
        )
    }
}
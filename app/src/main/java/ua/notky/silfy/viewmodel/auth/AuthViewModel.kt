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
import ua.notky.silfy.config.VALIDATION_EMAIL
import ua.notky.silfy.models.model.Profile
import ua.notky.silfy.models.observable.AuthModel
import ua.notky.silfy.models.states.AuthUiState
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import ua.notky.silfy.usecase.SaveDefaultDataUseCase
import ua.notky.silfy.usecase.profile.CreateProfileUseCase
import ua.notky.silfy.usecase.profile.ExistProfileUseCase
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@HiltViewModel
class AuthViewModel @Inject constructor(
    override val validation: ValidationService,
    private val dataStore: AppDataStorePreferences,
    private val existProfileUseCase: ExistProfileUseCase,
    private val createProfileUseCase: CreateProfileUseCase,
    private val defaultDataUseCase: SaveDefaultDataUseCase
) : BaseValidationViewModel() {
    private val model: AuthModel = AuthModel()

    private val _profileState: MutableLiveData<AuthUiState> = MutableLiveData()
    val profileState: LiveData<AuthUiState> = _profileState

    fun getEmail() = model.email.get()

    fun getEmptyModel(): AuthModel {
        model.email.set("")

        // todo for test auth data
//        @Deprecated(message = "for test")
//        if (BuildConfig.DEBUG) {
//            model.email.set("test@test.com")
//        }

        return model
    }

    fun onContinue() {
        viewModelScope.launch {
            if (isValidEmail()) {
                checkProfile()
            }
        }
    }

    private suspend fun checkProfile() {
        _profileState.postValue(AuthUiState.Loading)

        val params = ExistProfileUseCase.Params(model.email.get())

        when (val result = existProfileUseCase.check(params)) {
            is ResultState.Success.Result -> handleLoadedProfile(result.data)
            ResultState.Success.Empty -> _profileState.postValue(AuthUiState.Create)
            is ResultState.Failure -> _profileState.postValue(AuthUiState.Failure.ErrorCheck)
        }
    }

    fun onCreateProfile() {
        viewModelScope.launch {
            _profileState.postValue(AuthUiState.Loading)

            val params = CreateProfileUseCase.Params(model.email.get())

            when (val result = createProfileUseCase.create(params)) {
                is ResultState.Success.Result -> handleLoadedProfile(result.data, true)
                ResultState.Success.Empty -> _profileState.postValue(AuthUiState.Failure.Missing)
                is ResultState.Failure -> _profileState.postValue(AuthUiState.Failure.ErrorCreate)
            }
        }
    }

    private suspend fun handleLoadedProfile(profile: Profile, isCreated: Boolean = false) {
        if (profile.id != null) {
            dataStore.setProfileId(profile.id)

            if (isCreated) {
                defaultDataUseCase.fetch()
            }

            _profileState.postValue(AuthUiState.Loaded)
        } else {
            _profileState.postValue(AuthUiState.Failure.Missing)
        }
    }

    private fun isValidEmail(): Boolean {
        return addValidateData(
            listOf(
                ValidationModel(VALIDATION_EMAIL, model.email.get())
            )
        )
    }
}
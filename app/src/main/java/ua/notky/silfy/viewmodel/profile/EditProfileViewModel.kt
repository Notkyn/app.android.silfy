package ua.notky.silfy.viewmodel.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.models.model.EditProfileForm
import ua.notky.silfy.models.states.UpdateProfileUiState
import ua.notky.silfy.repository.db.dao.ProfileDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import ua.notky.silfy.usecase.profile.UpdateProfileUseCase
import ua.notky.silfy.usecase.profile.UpdateProfileUseCase.PhotoChange
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 22.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

/**
 * 5b Edit profile sheet of the active profile. Scoped to the sheet; nothing is saved until "Save".
 * A color replaces the photo, a picked photo replaces the color.
 */
@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val dataStore: AppDataStorePreferences,
    private val profileDao: ProfileDao,
    private val updateProfileUseCase: UpdateProfileUseCase
) : BaseViewModel() {

    private val _form = MutableLiveData<EditProfileForm>()
    val form: LiveData<EditProfileForm> = _form

    private val _uiState = MutableLiveData<UpdateProfileUiState>(UpdateProfileUiState.Idle)
    val uiState: LiveData<UpdateProfileUiState> = _uiState

    /** Photo of the saved profile */
    private var savedPhoto: String? = null

    init {
        viewModelScope.launch {
            val profile = dataStore.getProfileId()?.let { profileDao.getById(it) } ?: return@launch
            savedPhoto = profile.photo
            _form.value = EditProfileForm(profile.name, profile.avatarColor, profile.photo)
        }
    }

    fun setName(value: String) {
        val current = _form.value ?: return
        if (current.name != value) _form.value = current.copy(name = value)
    }

    fun selectColor(index: Int) {
        val current = _form.value ?: return
        _form.value = current.copy(
            colorIndex = index,
            photo = null,
            photoChange = if (savedPhoto != null) PhotoChange.Remove else PhotoChange.Keep
        )
    }

    fun selectPhoto(uri: String) {
        val current = _form.value ?: return
        _form.value = current.copy(photo = uri, photoChange = PhotoChange.Set(uri))
    }

    fun save() {
        val form = _form.value ?: return
        if (!form.isValid || _uiState.value != UpdateProfileUiState.Idle) return

        _uiState.value = UpdateProfileUiState.Saving
        viewModelScope.launch {
            val params = UpdateProfileUseCase.Params(form.name, form.colorIndex, form.photoChange)
            val result = updateProfileUseCase.update(params)
            _uiState.value = if (result.isSuccess) UpdateProfileUiState.Saved else UpdateProfileUiState.Failure
        }
    }

    /** Failures are shown once */
    fun consumeState() {
        _uiState.value = UpdateProfileUiState.Idle
    }
}

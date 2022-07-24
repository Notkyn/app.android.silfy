package ua.notky.silfy.viewmodel.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.models.observable.EditProfileModel
import ua.notky.silfy.models.states.UpdateProfileUiState
import ua.notky.silfy.usecase.profile.UpdateProfileUseCase
import ua.notky.silfy.usecase.profile.UploadProfilePhotoUseCase
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 22.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val uploadProfilePhotoUseCase: UploadProfilePhotoUseCase
) : BaseViewModel() {
    val model = EditProfileModel()

    private val _updateState: MutableLiveData<UpdateProfileUiState> = MutableLiveData()
    val updateState: LiveData<UpdateProfileUiState> = _updateState

    fun updateModel(firstName: String?, lastName: String?) {
        model.firstName.set(firstName)
        model.lastName.set(lastName)
    }

    fun updatePhoto(path: String?) {
        model.photoPath.set(path)
        model.oldPhotoPath = path
        checkOldData()
    }

    private fun checkOldData() {
        model.isOldData.set(model.oldPhotoPath != model.photoPath.get())
    }

    fun saveImage() {
        viewModelScope.launch {
            _updateState.postValue(UpdateProfileUiState.Updating)

            val params = UploadProfilePhotoUseCase.Params(model.photoPath.get())
            if (uploadProfilePhotoUseCase.upload(params).isSuccess) {
                _updateState.postValue(UpdateProfileUiState.Updated)
            } else {
                _updateState.postValue(UpdateProfileUiState.Failure.UpdateData)
            }
        }
    }

    fun loadImage(uri: Uri) {
        model.photoPath.set(uri.toString())
        checkOldData()
    }

    fun onSaveData() {
        viewModelScope.launch {
            _updateState.postValue(UpdateProfileUiState.Updating)

            val params = UpdateProfileUseCase.Params(
                model.firstName.get(),
                model.lastName.get()
            )

            if (updateProfileUseCase.update(params).isSuccess) {
                _updateState.postValue(UpdateProfileUiState.Updated)
            } else {
                _updateState.postValue(UpdateProfileUiState.Failure.UpdateData)
            }
        }
    }

    fun clearState() {
        _updateState.postValue(UpdateProfileUiState.Checking)
    }
}
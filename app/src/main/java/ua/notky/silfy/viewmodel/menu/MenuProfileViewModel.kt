package ua.notky.silfy.viewmodel.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.models.model.Profile
import ua.notky.silfy.models.states.DeleteProfileUiState
import ua.notky.silfy.repository.db.dao.ProfileDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import ua.notky.silfy.usecase.profile.DeleteProfileUseCase
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 26.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@HiltViewModel
class MenuProfileViewModel @Inject constructor(
    profileDao: ProfileDao,
    private val deleteProfileUseCase: DeleteProfileUseCase,
    private val dataStore: AppDataStorePreferences
) : BaseViewModel() {
    val profiles: LiveData<List<Profile>> = profileDao.getAll()

    private val _deleteState: MutableLiveData<DeleteProfileUiState> = MutableLiveData()
    val deleteState: LiveData<DeleteProfileUiState> = _deleteState

    fun delete(profile: Profile) {
        _deleteState.postValue(DeleteProfileUiState.Deleting)

        viewModelScope.launch {
            val params = DeleteProfileUseCase.Params(profile)

            if (deleteProfileUseCase.delete(params).isSuccess) {
                checkLogoutState(profile.id)
            } else {
                _deleteState.postValue(DeleteProfileUiState.Failure)
            }
        }
    }

    private suspend fun checkLogoutState(id: Int?) {
        val currentProfileId = dataStore.getProfileId()
        if (currentProfileId == id) {
            dataStore.removeProfileId()
            _deleteState.postValue(DeleteProfileUiState.LogOut)
        } else {
            _deleteState.postValue(DeleteProfileUiState.Deleted)
        }
    }
}
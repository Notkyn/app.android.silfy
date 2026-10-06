package ua.notky.silfy.viewmodel.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.models.model.Profile
import ua.notky.silfy.models.model.ProfileStats
import ua.notky.silfy.models.states.DeleteProfileUiState
import ua.notky.silfy.repository.db.dao.ProfileDao
import ua.notky.silfy.repository.db.dao.word.WordListDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import ua.notky.silfy.usecase.profile.ActiveProfileUseCase
import ua.notky.silfy.usecase.profile.DeleteProfileUseCase
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

/** 5a Profile of the active profile (live: the edit sheet saves straight to Room) + 5d delete */
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataStore: AppDataStorePreferences,
    private val profileDao: ProfileDao,
    private val wordListDao: WordListDao,
    private val deleteProfileUseCase: DeleteProfileUseCase,
    private val activeProfileUseCase: ActiveProfileUseCase
) : BaseViewModel() {

    private val profileId = MutableLiveData<Int>()

    val profile: LiveData<Profile> = profileId.switchMap { profileDao.getLiveDataById(it) }

    val stats: LiveData<ProfileStats> = profileId.switchMap { wordListDao.getProfileStats(it) }

    private val _deleteState = MutableLiveData<DeleteProfileUiState>(DeleteProfileUiState.Idle)
    val deleteState: LiveData<DeleteProfileUiState> = _deleteState

    init {
        viewModelScope.launch {
            dataStore.getProfileId()?.let { profileId.value = it }
        }
    }

    fun delete() {
        val current = profile.value ?: return
        if (_deleteState.value != DeleteProfileUiState.Idle) return

        _deleteState.value = DeleteProfileUiState.Deleting
        viewModelScope.launch {
            val result = deleteProfileUseCase.delete(DeleteProfileUseCase.Params(current))
            if (result.isSuccess) {
                // Resets the app language to the system one: the activity is recreated, this view model is not
                activeProfileUseCase.clear()
                _deleteState.value = DeleteProfileUiState.Deleted
            } else {
                _deleteState.value = DeleteProfileUiState.Failure
            }
        }
    }

    /** Failures are shown once */
    fun consumeState() {
        _deleteState.value = DeleteProfileUiState.Idle
    }
}

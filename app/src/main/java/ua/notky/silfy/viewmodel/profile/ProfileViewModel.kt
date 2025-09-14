package ua.notky.silfy.viewmodel.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.models.model.Profile
import ua.notky.silfy.models.observable.ProfileModel
import ua.notky.silfy.repository.db.dao.ProfileDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileDao: ProfileDao,
    private val dataStore: AppDataStorePreferences
) : BaseViewModel() {
    val model = ProfileModel()

    private val _profileIdQuery: MutableLiveData<Int> = MutableLiveData()
    val profile: LiveData<Profile> = _profileIdQuery.switchMap { id ->
        profileDao.getLiveDataById(id)
    }

    private val _logout: MutableLiveData<Boolean> = MutableLiveData()
    val logout: LiveData<Boolean> = _logout

    fun fetchCurrentProfile() {
        viewModelScope.launch {
            dataStore.getProfileId()?.let { _profileIdQuery.postValue(it) }
        }
    }

    fun updateProfile(profile: Profile) {
        model.firstName.set(profile.firstName)
        model.lastName.set(profile.lastName)
        model.avatar.set(profile.avatar)
        model.email.set(profile.email)
        model.createTime.set(profile.createTime)
    }

    fun onLogout() {
        viewModelScope.launch {
            dataStore.removeProfileId()
            _logout.postValue(true)
        }
    }
}
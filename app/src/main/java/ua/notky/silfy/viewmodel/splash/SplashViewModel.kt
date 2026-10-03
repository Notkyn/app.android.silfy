package ua.notky.silfy.viewmodel.splash

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.BuildConfig
import ua.notky.silfy.di.FirebaseModule.KEY_VERSION_CODE
import ua.notky.silfy.repository.db.dao.ProfileDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import ua.notky.silfy.util.AppLocale
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val datastore: AppDataStorePreferences,
    private val profileDao: ProfileDao,
    private val remoteConfig: FirebaseRemoteConfig
) : BaseViewModel() {
    val version: ObservableField<String> = ObservableField("")

    private val _loggedState: MutableLiveData<Boolean> = MutableLiveData()
    val loggedState: LiveData<Boolean> = _loggedState

    private val _readyUpdate: MutableLiveData<Boolean> = MutableLiveData()
    val readyUpdate: LiveData<Boolean> = _readyUpdate

    override fun init() {
        super.init()
        initModel()
    }

    private fun initModel() {
        val testVersion = "v ${BuildConfig.VERSION_NAME}"
        version.set(testVersion)
    }

    fun checkLoggedUser() {
        viewModelScope.launch {
            val profile = datastore.getProfileId()?.let { profileDao.getById(it) }

            // Profiles from 1.x (and the first start after the update) have no stored app locale yet
            if (profile != null && !AppLocale.isSet()) AppLocale.apply(profile.language)

            _loggedState.postValue(profile != null)
        }
    }

    fun checkAvailableUpdate() {
        viewModelScope.launch {
            remoteConfig.fetchAndActivate().addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    val remoteVersionCode = remoteConfig.getLong(KEY_VERSION_CODE)
                    _readyUpdate.postValue(BuildConfig.VERSION_CODE < remoteVersionCode)
                } else {
                    _readyUpdate.postValue(false)
                }
            }
        }
    }
}
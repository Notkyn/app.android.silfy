package ua.notky.silfy.viewmodel.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.BuildConfig
import ua.notky.silfy.di.FirebaseModule.KEY_VERSION_CODE
import ua.notky.silfy.models.states.StartRoute
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

    private val _route: MutableLiveData<StartRoute> = MutableLiveData()
    val route: LiveData<StartRoute> = _route

    private val _readyUpdate: MutableLiveData<Boolean> = MutableLiveData()
    val readyUpdate: LiveData<Boolean> = _readyUpdate

    fun resolveRoute() {
        viewModelScope.launch {
            val profile = datastore.getProfileId()?.let { profileDao.getById(it) }

            val route = when {
                profile != null -> {
                    // Profiles from 1.x (and the first start after the update) have no stored app locale yet
                    if (!AppLocale.isSet()) AppLocale.apply(profile.language)
                    StartRoute.MAIN
                }
                !datastore.isWelcomeShown() && profileDao.count() == 0 -> StartRoute.WELCOME
                else -> StartRoute.PROFILES
            }

            _route.postValue(route)
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

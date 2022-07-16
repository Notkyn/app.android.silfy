package ua.notky.silfy.viewmodel.splash

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.BuildConfig
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val datastore: AppDataStorePreferences
) : BaseViewModel() {
    val version: ObservableField<String> = ObservableField("")

    private val _loggedState: MutableLiveData<Boolean> = MutableLiveData()
    val loggedState: LiveData<Boolean> = _loggedState

    override fun init() {
        super.init()
        initModel()
        checkLoggedUser()
    }

    private fun initModel() {
        val testVersion = "v ${BuildConfig.VERSION_NAME}"
        version.set(testVersion)
    }

    private fun checkLoggedUser() {
        viewModelScope.launch {
            _loggedState.postValue(datastore.getProfileId() != null)
        }
    }
}
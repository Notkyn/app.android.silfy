package ua.notky.silfy.viewmodel.menu

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.models.model.Profile
import ua.notky.silfy.repository.db.dao.ProfileDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import ua.notky.silfy.usecase.menu.ContactUsUseCase
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

/** 6a Menu: the active profile card (live: renamed in 5b) and Contact us */
@HiltViewModel
class MenuViewModel @Inject constructor(
    private val dataStore: AppDataStorePreferences,
    private val profileDao: ProfileDao,
    private val contactUsUseCase: ContactUsUseCase
) : BaseViewModel() {

    private val profileId = MutableLiveData<Int>()

    val profile: LiveData<Profile> = profileId.switchMap { profileDao.getLiveDataById(it) }

    init {
        viewModelScope.launch {
            dataStore.getProfileId()?.let { profileId.value = it }
        }
    }

    fun sendContactUsEmail(context: Context) {
        contactUsUseCase.send(context)
    }
}

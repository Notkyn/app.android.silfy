package ua.notky.silfy.viewmodel.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.config.ACTION_LOGOUT
import ua.notky.silfy.models.model.Profile
import ua.notky.silfy.util.help.getTempProfiles

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 26.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class MenuProfileViewModel : BaseViewModel() {
    private val _profiles: MutableLiveData<List<Profile>> = MutableLiveData()
    val profiles: LiveData<List<Profile>> = _profiles

    fun fetchData() {
        val list = getTempProfiles()

        if(list.isNotEmpty()) {
            _profiles.postValue(list)
        } else {
            setAction(ACTION_LOGOUT)
        }
    }

    fun delete(profile: Profile) {
        val list: MutableList<Profile> = mutableListOf()
        _profiles.value?.let { list.addAll(it) }

        if (list.contains(profile)) {
            list.remove(profile)
        }

        if(list.isNotEmpty()) {
            _profiles.postValue(list)
        } else {
            setAction(ACTION_LOGOUT)
        }
    }
}
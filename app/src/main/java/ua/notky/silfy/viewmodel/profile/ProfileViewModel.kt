package ua.notky.silfy.viewmodel.profile

import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.models.observable.ProfileModel
import ua.notky.silfy.util.help.getTempProfile

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

class ProfileViewModel : BaseViewModel() {
    val model = ProfileModel()

    fun fetchCurrentProfile() {
        val profile = getTempProfile()
        model.firstName.set(profile.firstName)
        model.lastName.set(profile.lastName)
        model.avatar.set(profile.avatar)

    }
}
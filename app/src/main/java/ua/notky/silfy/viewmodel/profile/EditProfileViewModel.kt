package ua.notky.silfy.viewmodel.profile

import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.models.observable.EditProfileModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 22.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class EditProfileViewModel : BaseViewModel() {
    val model = EditProfileModel()

    fun updateModel(firstName: String?, lastName: String?) {
        model.firstName.set(firstName)
        model.lastName.set(lastName)
    }
}
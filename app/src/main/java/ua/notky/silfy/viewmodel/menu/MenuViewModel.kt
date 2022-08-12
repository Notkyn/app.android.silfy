package ua.notky.silfy.viewmodel.menu

import android.content.Context
import dagger.hilt.android.lifecycle.HiltViewModel
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.BuildConfig
import ua.notky.silfy.models.observable.MenuModel
import ua.notky.silfy.usecase.menu.ContactUsUseCase
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val contactUsUseCase: ContactUsUseCase,
) : BaseViewModel() {
    val model = MenuModel()

    fun fetchData() {
        model.version.set("v ${BuildConfig.VERSION_NAME}")
    }

    fun sendContactUsEmail(context: Context) {
        contactUsUseCase.send(context)
    }
}
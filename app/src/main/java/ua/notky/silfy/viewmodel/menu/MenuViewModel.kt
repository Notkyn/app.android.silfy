package ua.notky.silfy.viewmodel.menu

import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.BuildConfig
import ua.notky.silfy.models.observable.MenuModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class MenuViewModel : BaseViewModel() {
    val model = MenuModel()

    fun fetchData() {
        model.version.set("v ${BuildConfig.VERSION_NAME}")
    }
}
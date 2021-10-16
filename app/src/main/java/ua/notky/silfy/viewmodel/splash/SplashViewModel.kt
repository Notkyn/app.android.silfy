package ua.notky.silfy.viewmodel.splash

import androidx.databinding.ObservableField
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.BuildConfig

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class SplashViewModel : BaseViewModel() {
    val version: ObservableField<String> = ObservableField("")

    override fun init() {
        super.init()
        initModel()
    }

    private fun initModel() {
        val testVersion = "v ${BuildConfig.VERSION_NAME}"
        version.set(testVersion)
    }
}
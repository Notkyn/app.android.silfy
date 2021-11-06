package ua.notky.base.ui.init.ui

import android.os.Bundle
import ua.notky.base.viewmodel.ViewModelSet

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 06.11.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */
interface InitializationActivity : BaseInitializationInterface {
    fun init(savedInstanceState: Bundle?)
    fun buildViewModels(): ViewModelSet { return ViewModelSet.Builder().build() }
}
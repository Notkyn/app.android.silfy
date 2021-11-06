package ua.notky.base.ui.init.ui

import ua.notky.base.viewmodel.ViewModelSet

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 06.11.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */
interface InitializationFragment : BaseInitializationInterface {
    fun init()
    fun buildViewModels(): ViewModelSet
}
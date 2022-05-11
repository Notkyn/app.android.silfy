package ua.notky.base.ui.init

import android.os.Bundle
import ua.notky.base.viewmodel.ViewModelSet

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 06.11.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */
interface BaseInitialization {
    fun initialize(savedInstanceState: Bundle?)

    fun initializeViews() {}
    fun initializeViewModels() {}
    fun initializeListeners() {}
    fun initializeData() {}

    fun injectViewModels(): ViewModelSet { return ViewModelSet.Builder().build() }
}
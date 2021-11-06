package ua.notky.base.ui.init.viewmodel

import androidx.lifecycle.LiveData
import ua.notky.base.failure.Failure
import ua.notky.base.viewmodel.ViewModelAction

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 06.11.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */
interface InitializationViewModel {
    fun init(){}
    fun getFailure(): LiveData<Failure?>?
    fun getAction(): LiveData<ViewModelAction>
}
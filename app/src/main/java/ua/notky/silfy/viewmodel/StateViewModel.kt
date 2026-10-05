package ua.notky.silfy.viewmodel

import ua.notky.base.viewmodel.state.BaseStateViewModel
import ua.notky.silfy.BuildConfig
import ua.notky.silfy.models.observable.StateModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

class StateViewModel : BaseStateViewModel<StateModel>() {
    override val state: StateModel = StateModel()

    fun setLoading(value: Boolean) {
        state.isLoading.set(value)
    }

    fun updatePresentValue(value: Boolean = false) {
        state.isPresentValue.set(value)
    }

    fun updateInfoMenuStates(position: Int, pages: Int) {
        state.isPreviousInfo.set(position > 0)
        state.isNextInfo.set(position != pages - 1)
    }

    fun checkAdmin() {
        state.isAdmin.set(BuildConfig.DEBUG)
    }
}
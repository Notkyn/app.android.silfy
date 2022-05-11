package ua.notky.base.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ua.notky.base.network.util.checkNetworkResponse
import ua.notky.base.network.util.handleNetworkResponse
import ua.notky.base.failure.Failure
import ua.notky.base.failure.FailureService
import ua.notky.base.network.api.model.BaseResponse
import ua.notky.base.network.api.response.NetworkResponse
import ua.notky.base.ui.init.viewmodel.InitializationViewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

abstract class BaseViewModel : ViewModel(), InitializationViewModel {
    override fun init() {
        clearAction()
        clearFailure()
    }

    /* Failure */
    protected open val failureService: FailureService? = null

    override fun getFailure(): LiveData<Failure>? { return failureService?.getLiveData() }

    private fun clearFailure() {
        failureService?.clear()
    }

    /* Action Mode */
    private val _action: MutableLiveData<ViewModelAction> = MutableLiveData()

    override fun getAction(): LiveData<ViewModelAction> {
        return _action
    }

    fun setAction(type: Int) {
        _action.postValue(ViewModelAction(type))
    }

    private fun clearAction() {
        _action.value = null
    }

    /* Network */
    protected fun <K : BaseResponse> handleResponse(
        response: NetworkResponse<K>
    ): K? {
        return handleNetworkResponse(response) {
            failureService?.create(it)
        }
    }

    protected fun <K : BaseResponse> checkResponse(
        response: NetworkResponse<K>
    ): Boolean {
        return checkNetworkResponse(response) {
            failureService?.create(it)
        }
    }

    protected fun handleException(ex: Throwable) {
        failureService?.create(ex)
    }
}
package ua.notky.base.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ua.notky.base.changeable.ActionMode
import ua.notky.base.failure.Failure
import ua.notky.base.failure.FailureResourceService
import ua.notky.base.util.toFailure

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

abstract class BaseViewModel : ViewModel() {
    open fun init() {
        clearActionMode()
        clearFailure()
    }

    /* Failure */
    protected open val failureService: FailureResourceService? = null

    private val _failure: MutableLiveData<Failure?> = MutableLiveData()
    fun getFailure(): LiveData<Failure?> { return _failure }

    private fun clearFailure() {
        _failure.value = null
    }

    /* Action Mode */
    private val _actionMode: MutableLiveData<ActionMode> = MutableLiveData()
    fun getActionMode(): LiveData<ActionMode> {
        return _actionMode
    }

    fun setAction(action: ActionMode) {
        _actionMode.postValue(action)
    }

    private fun clearActionMode() {
        _actionMode.value = null
    }

    /* Network */
//    protected fun <K : BaseResponse> handleResponse(
//        response: NetworkResponse<K>
//    ): K? {
//        return handleNetworkResponse(response) {
//            checkFailureInfo(it)
//        }
//    }
//
//    protected fun <K : BaseResponse> checkResponse(
//        response: NetworkResponse<K>
//    ): Boolean {
//        return checkNetworkResponse(response) {
//            checkFailureInfo(it)
//        }
//    }

    /* Check Failure */
    private fun checkFailure(info: Failure) {
        info.localizeMsg = failureService?.getLocalizeMsg(info.type)

        _failure.postValue(info)
    }

    protected fun handleException(ex: Throwable) {
        val info = ex.toFailure()

        info.localizeMsg = failureService?.getLocalizeMsg(info.type)

        _failure.postValue(info)
    }
}
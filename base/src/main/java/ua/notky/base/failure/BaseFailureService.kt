package ua.notky.base.failure

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ua.notky.base.network.api.response.NetworkError

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 06.11.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

abstract class BaseFailureService : FailureService {
    protected abstract val context: Context

    private val _failureLiveData: MutableLiveData<Failure?> = MutableLiveData()

    override fun getFailureLiveData(): LiveData<Failure?> {
        return _failureLiveData
    }

    override fun setFailure(failure: Failure?) {
        _failureLiveData.postValue(failure)
    }

    override fun clearData() {
        _failureLiveData.value = null
    }

    override fun createExceptionFailure(ex: Throwable) {
        ex.printStackTrace()
        putFailureToLiveData(Failure(FAILURE_EXCEPTION, ex.localizedMessage))
    }

    override fun createNetworkFailure(error: NetworkError) {
        when(error.type) {
            FAILURE_API -> getApiFailure(error)
            FAILURE_HTTP -> getHttpFailure(error)
            FAILURE_EXCEPTION -> getExceptionFailure(error)
            FAILURE_APP -> getAppFailure(error)
        }
    }

    private fun getAppFailure(error: NetworkError) {
        error.exception?.printStackTrace()
        putFailureToLiveData(Failure(FAILURE_APP, error.exception?.localizedMessage))
    }

    private fun getExceptionFailure(error: NetworkError) {
        error.exception?.printStackTrace()
        putFailureToLiveData(Failure(FAILURE_EXCEPTION, error.exception?.localizedMessage))
    }

    private fun getHttpFailure(error: NetworkError) {
        val result = StringBuilder()
            .append(error.httpCode ?: -1)
            .append(": ")
            .append(error.msg)
            .toString()

        putFailureToLiveData(Failure(FAILURE_HTTP, result))
    }

    private fun getApiFailure(error: NetworkError) {
        putFailureToLiveData(
            createApiFailure(
                error.httpCode,
                error.apiCode,
                error.msg
            )
        )
    }

    private fun putFailureToLiveData(failure: Failure){
        getLocalizeMsg(failure.type)?.let { failure.localizeMsg = it}

        setFailure(failure)
    }
}
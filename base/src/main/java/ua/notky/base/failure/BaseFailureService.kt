package ua.notky.base.failure

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ua.notky.base.failure.handler.ApiFailureHandler
import ua.notky.base.failure.handler.ExceptionFailureHandler
import ua.notky.base.failure.handler.HttpFailureHandler
import ua.notky.base.network.api.response.NetworkError

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 06.11.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

abstract class BaseFailureService(
    private val apiHandler: ApiFailureHandler?,
    private val httpHandler: HttpFailureHandler?,
    private val exHandler: ExceptionFailureHandler?
) : FailureService {

    private val _failureLiveData: MutableLiveData<Failure?> = MutableLiveData()

    override fun getLiveData(): LiveData<Failure?> {
        return _failureLiveData
    }

    override fun put(failure: Failure?) {
        _failureLiveData.postValue(failure)
    }

    override fun clear() {
        _failureLiveData.value = null
    }

    override fun handleError(ex: Throwable) {
        ex.printStackTrace()
        put(exHandler?.createExceptionFailure(ex.message, ex))
    }

    override fun handleError(error: NetworkError) {
        when (error.category) {
            Failure.Category.API -> getApiFailure(error)
            Failure.Category.HTTP -> getHttpFailure(error)
            Failure.Category.EXCEPTION -> getExceptionFailure(error)
            Failure.Category.APP -> getExceptionFailure(error)
        }
    }

    private fun getExceptionFailure(error: NetworkError) {
        error.exception?.printStackTrace()
        put(exHandler?.createExceptionFailure(error.msg, error.exception))
    }

    private fun getHttpFailure(error: NetworkError) {
        val result = StringBuilder()
            .append(error.httpCode ?: -1)
            .append(": ")
            .append(error.msg)
            .toString()

        put(httpHandler?.createHttpFailure(error.httpCode, result, error.exception))
    }

    private fun getApiFailure(error: NetworkError) {
        put(
            apiHandler?.createApiFailure(
                error.httpCode,
                error.apiCode,
                error.msg,
                error.exception
            )
        )
    }
}
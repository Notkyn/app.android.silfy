package ua.notky.base.failure

import androidx.lifecycle.LiveData
import ua.notky.base.failure.handler.ApiFailureHandler
import ua.notky.base.failure.handler.ExceptionFailureHandler
import ua.notky.base.failure.handler.HttpFailureHandler
import ua.notky.base.network.api.response.NetworkError

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

interface FailureService {
    fun put(failure: Failure?)
    fun clear()
    fun getLiveData(): LiveData<Failure?>

    // Failures
    fun handleError(error: NetworkError)
    fun handleError(ex: Throwable)

    data class Builder(
        private var _apiHandler: ApiFailureHandler? = null,
        private var _httpHandler: HttpFailureHandler? = null,
        private var _exHandler: ExceptionFailureHandler? = null
    ) {

        fun setApiHandler(handler: ApiFailureHandler?) = apply {
            _apiHandler = handler
        }

        fun setHttpHandler(handler: HttpFailureHandler?) = apply {
            _httpHandler = handler
        }

        fun setExceptionHandler(handler: ExceptionFailureHandler?) = apply {
            _exHandler = handler
        }

        fun build(): FailureService {
            return object : BaseFailureService(
                _apiHandler, _httpHandler, _exHandler
            ) {}
        }
    }
}
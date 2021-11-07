package ua.notky.base.changeable

import ua.notky.base.failure.*
import ua.notky.base.failure.FAILURE_APP
import ua.notky.base.network.api.model.BaseResponse
import ua.notky.base.network.api.response.NetworkError
import ua.notky.base.network.api.response.NetworkResponse

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

fun <K : BaseResponse> handleNetworkResponse(
    response: NetworkResponse<K>,
    errorBlock: (error: NetworkError) -> Unit
): K? {
    return when (response) {
        is NetworkResponse.Success.Result -> response.body
        is NetworkResponse.Success.Empty -> null
        is NetworkResponse.Failure -> {
            errorBlock.invoke(response.toNetworkError())
            null
        }
    }
}

fun <K : BaseResponse> checkNetworkResponse(
    response: NetworkResponse<K>,
    errorBlock: (error: NetworkError) -> Unit
): Boolean {
    return when (response) {
        is NetworkResponse.Success -> true
        is NetworkResponse.Failure -> {
            errorBlock.invoke(response.toNetworkError())
            false
        }
    }
}

private fun NetworkResponse.Failure.toNetworkError(): NetworkError {
    return when (this) {
        is NetworkResponse.Failure.ApiError ->
            NetworkError(FAILURE_API, this.httpCode, this.code, this.msg)
        is NetworkResponse.Failure.HttpError ->
            NetworkError(FAILURE_HTTP, this.code, null, this.msg)
        is NetworkResponse.Failure.NetworkError ->
            NetworkError(FAILURE_EXCEPTION, null, null, null, this.error)
        is NetworkResponse.Failure.Error ->
            NetworkError(FAILURE_APP, null, null, null, this.error)
    }
}
package ua.notky.base.network.util

import ua.notky.base.failure.Failure
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
            NetworkError(Failure.Category.API, this.httpCode, this.code, this.msg)
        is NetworkResponse.Failure.HttpError ->
            NetworkError(Failure.Category.HTTP, this.code, null, this.msg)
        is NetworkResponse.Failure.NetworkError ->
            NetworkError(Failure.Category.EXCEPTION, null, null, null, this.error)
        is NetworkResponse.Failure.Error ->
            NetworkError(Failure.Category.EXCEPTION, null, null, null, this.error)
    }
}
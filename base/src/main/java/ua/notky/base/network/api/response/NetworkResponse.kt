package ua.notky.base.network.api.response

import ua.notky.base.network.api.model.BaseResponse
import java.io.IOException

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 07.11.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

sealed class NetworkResponse<out T : BaseResponse> {
    sealed class Success<K : BaseResponse> : NetworkResponse<K>() {
        data class Result<K : BaseResponse>(val body: K) : Success<K>()
        object Empty : Success<Nothing>()
    }
    sealed class Failure : NetworkResponse<Nothing>() {
        data class ApiError(val httpCode: Int?, val code: Int?, val msg: String?) : Failure()
        data class HttpError(val code: Int?, val msg: String?) : Failure()
        data class NetworkError(val error: IOException) : Failure()
        data class Error(val error: Throwable?) : Failure()
    }
}
package ua.notky.base.network.api.response

import okhttp3.Request
import okhttp3.ResponseBody
import okio.IOException
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ua.notky.base.network.api.model.BaseResponse
import ua.notky.base.network.util.transformErrorBody

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 07.11.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

internal class NetworkResponseCall<S : BaseResponse>(
    private val delegate: Call<S>
) : Call<NetworkResponse<S>> {

    override fun enqueue(callback: Callback<NetworkResponse<S>>) {
        return delegate.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                val body = response.body()
                val errorBody = response.errorBody()

                if (response.isSuccessful) {
                    checkedSuccessResponse(body, callback)
                } else {
                    checkedFailResponse(errorBody, response, callback)
                }
            }

            override fun onFailure(call: Call<S>, throwable: Throwable) {
                throwable.printStackTrace()
                getErrorResult(throwable, callback)
            }
        })
    }

    /* Checked response */

    private fun checkedFailResponse(
        errorBody: ResponseBody?,
        response: Response<S>,
        callback: Callback<NetworkResponse<S>>
    ) {
        val errorModel = transformErrorBody(errorBody)

        if (errorModel != null) {
            val msg = errorModel.errorDescription ?: errorModel.message
            getFailApiResult(response.code(), errorModel.statusCode, msg, callback)
        } else {
            getFailHttpResult(response.code(), response.message(), callback)
        }
    }

    private fun checkedSuccessResponse(body: S?, callback: Callback<NetworkResponse<S>>) {
        if(body != null) {
            checkedApiResponse(body, callback)
        } else {
            getSuccessEmptyResult(callback)
        }
    }

    private fun checkedApiResponse(body: S, callback: Callback<NetworkResponse<S>>) {
        if(body.isSuccessful()) {
            getSuccessResult(body, callback)
        } else {
            val msg = body.errorDescription ?: body.message
            getFailApiResult(null, body.statusCode, msg, callback)
        }
    }

    /* Response */

    private fun getSuccessResult(body: S, callback: Callback<NetworkResponse<S>>) {
        callback.onResponse(
            this@NetworkResponseCall,
            Response.success(NetworkResponse.Success.Result(body))
        )
    }

    private fun getSuccessEmptyResult(callback: Callback<NetworkResponse<S>>) {
        callback.onResponse(
            this@NetworkResponseCall,
            Response.success(NetworkResponse.Success.Empty)
        )
    }

    private fun getFailApiResult(httpCode: Int?, code: Int?, msg: String?, callback: Callback<NetworkResponse<S>>) {
        callback.onResponse(
            this@NetworkResponseCall,
            Response.success(NetworkResponse.Failure.ApiError(httpCode, code, msg))
        )
    }

    private fun getFailHttpResult(code: Int?, msg: String?, callback: Callback<NetworkResponse<S>>) {
        callback.onResponse(
            this@NetworkResponseCall,
            Response.success(NetworkResponse.Failure.HttpError(code, msg))
        )
    }

    private fun getErrorResult(throwable: Throwable, callback: Callback<NetworkResponse<S>>) {
        throwable.printStackTrace()

        val networkResponse = when (throwable) {
            is IOException -> NetworkResponse.Failure.NetworkError(throwable)
            else -> NetworkResponse.Failure.Error(throwable)
        }

        callback.onResponse(
            this@NetworkResponseCall,
            Response.success(networkResponse)
        )
    }

    /* Other */
    override fun isExecuted() = delegate.isExecuted

    override fun clone() = NetworkResponseCall(delegate.clone())

    override fun isCanceled() = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<NetworkResponse<S>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}
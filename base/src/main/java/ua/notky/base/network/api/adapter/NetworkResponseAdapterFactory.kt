package ua.notky.base.network.api.adapter

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import ua.notky.base.network.api.model.BaseResponse
import ua.notky.base.network.api.response.NetworkResponse
import java.lang.IllegalStateException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 07.11.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

class NetworkResponseAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *> {

        // suspend functions wrap the response type in `Call`
        if (Call::class.java != getRawType(returnType)) {
            throw IllegalStateException("Function don`t have suspend marker, must be Call type, but is type: [$returnType]")
        }

        // check first that the return type is `ParameterizedType`
        check(returnType is ParameterizedType) {
            "return type must be parameterized as Call<NetworkResponse<<Foo>> or Call<NetworkResponse<out Foo>>"
        }

        // get the response type inside the `Call` type
        val responseType = getParameterUpperBound(0, returnType)
        // if the response type is not ApiResponse then we can't handle this type, so we return null
        if (getRawType(responseType) != NetworkResponse::class.java) {
            throw IllegalStateException("response type must be is NetworkResponse")
        }

        // the response type is ApiResponse and should be parameterized
        check(responseType is ParameterizedType) { "Response must be parameterized as NetworkResponse<Foo> or NetworkResponse<out Foo>" }

        val successBodyType = getParameterUpperBound(0, responseType)

        return NetworkResponseAdapter<BaseResponse>(successBodyType)
    }
}
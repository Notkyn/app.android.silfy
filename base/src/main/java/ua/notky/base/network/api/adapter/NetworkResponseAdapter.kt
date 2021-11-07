package ua.notky.base.network.api.adapter

import retrofit2.Call
import retrofit2.CallAdapter
import ua.notky.base.network.api.model.BaseResponse
import ua.notky.base.network.api.response.NetworkResponse
import ua.notky.base.network.api.response.NetworkResponseCall
import java.lang.reflect.Type

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 07.11.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

class NetworkResponseAdapter<S : BaseResponse>(
    private val successType: Type
) : CallAdapter<S, Call<NetworkResponse<S>>> {

    override fun responseType(): Type = successType

    override fun adapt(call: Call<S>): Call<NetworkResponse<S>> {
        return NetworkResponseCall(call)
    }
}
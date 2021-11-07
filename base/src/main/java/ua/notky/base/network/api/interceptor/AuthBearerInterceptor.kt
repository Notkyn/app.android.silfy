package ua.notky.base.network.api.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import ua.notky.base.network.util.toBearerAuthHeader

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 07.11.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

class AuthBearerInterceptor(
    private val prefs: AuthNetworkPreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if(request.header(NO_AUTH_HEADER) == null){
            request = request.newBuilder()
                .addHeader(AUTH_HEADER, prefs.getAuthToken().toBearerAuthHeader())
                .build()
        }

        return chain.proceed(request)
    }

    companion object {
        const val NO_AUTH_HEADER = "No-Authentication"
        const val AUTH_HEADER = "Authorization"
    }
}
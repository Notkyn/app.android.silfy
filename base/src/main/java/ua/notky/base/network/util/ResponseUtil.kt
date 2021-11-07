package ua.notky.base.network.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import ua.notky.base.network.api.model.BaseResponse

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 07.11.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

fun transformErrorBody(errorBody: ResponseBody?): BaseResponse? {
    return try {
        val gson = Gson()
        val type = object : TypeToken<BaseResponse>() {}.type

        gson.fromJson(errorBody?.charStream(), type)
    } catch (exception: Throwable) {
        null
    }
}
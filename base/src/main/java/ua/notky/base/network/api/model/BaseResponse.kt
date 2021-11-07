package ua.notky.base.network.api.model

import com.squareup.moshi.Json

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 07.11.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

open class BaseResponse(
    @property:Json(name = "statusCode") var statusCode: Int? = -1,
    @property:Json(name = "status") var status: Boolean? = false,
    @property:Json(name = "errorDescription") var errorDescription: String? = null,
    @property:Json(name = "message") var message: String? = null
) {
    fun isSuccessful(): Boolean {
        return status == true
    }
}
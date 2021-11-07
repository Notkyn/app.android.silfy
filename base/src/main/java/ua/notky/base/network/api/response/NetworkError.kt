package ua.notky.base.network.api.response

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 07.11.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

data class NetworkError(
    val type: Int,
    val httpCode: Int? = null,
    val apiCode: Int? = null,
    val msg: String? = null,
    val exception: Throwable? = null
)
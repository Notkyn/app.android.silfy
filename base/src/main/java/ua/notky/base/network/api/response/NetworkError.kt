package ua.notky.base.network.api.response

import ua.notky.base.failure.Failure

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 07.11.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

data class NetworkError(
    val category: Failure.Category,
    val httpCode: Int? = null,
    val apiCode: Int? = null,
    val msg: String? = null,
    val exception: Throwable? = null
)
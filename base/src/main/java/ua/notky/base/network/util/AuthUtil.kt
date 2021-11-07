package ua.notky.base.network.util

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 07.11.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

fun String.toBearerAuthHeader(): String {
    return "Bearer $this"
}
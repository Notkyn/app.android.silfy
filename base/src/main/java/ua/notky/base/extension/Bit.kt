package ua.notky.base.extension

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

fun String?.toBoolean(): Boolean {
    return this == "1"
}

fun Boolean?.toBit(): String {
    return when (this) {
        true -> "1"
        else -> "0"
    }
}
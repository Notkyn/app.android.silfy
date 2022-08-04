package ua.notky.silfy.extension

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 09.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

fun String?.parseToInt(default: Int = 0): Int {
    return try {
        this?.toInt() ?: default
    } catch (ex: Exception) {
        ex.printStackTrace()
        default
    }
}
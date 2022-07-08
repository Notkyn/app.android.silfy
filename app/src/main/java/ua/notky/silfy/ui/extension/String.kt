package ua.notky.silfy.ui.extension

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 09.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

fun String?.parseToInt(): Int {
    return if(this.isNullOrEmpty()) {
        0
    } else {
        try {
            this.toInt()
        } catch (ex: Exception) {
            ex.printStackTrace()
            0
        }
    }
}
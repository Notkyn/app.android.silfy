package ua.notky.silfy.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 21.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
object TimeUtils {
    const val PATTERN_DATE = "dd.MM.yyyy"

    fun format(pattern: String, time: Long): String {
        val sdf = SimpleDateFormat(pattern)

        return sdf.format(Date(time))
    }
}
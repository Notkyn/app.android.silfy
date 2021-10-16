package ua.notky.base.util

import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

const val LOG_TAG: String = "APP_LOG"
const val LOG_TAG_OLD: String = "APP_LOG_OLD"

const val timePattern: String = "hh:mm:ss"

fun printLog(msg: String) {
    Timber.tag(LOG_TAG).i("${getFormatCurrentTime()} - $msg")
//    Log.i(LOG_TAG_OLD, "${getFormatCurrentTime()} - $msg")
}

fun printLog(obj: Any?) {
    printLog(obj.toString())
}

fun printLog(name: String, value: String) {
    printLog("${getFormatCurrentTime()} - Test message: [$name=$value]")
}

private fun getFormatCurrentTime(): String {
    return SimpleDateFormat(timePattern, Locale.ENGLISH).format(Date())
}
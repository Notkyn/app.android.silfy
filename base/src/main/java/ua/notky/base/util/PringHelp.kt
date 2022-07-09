package ua.notky.base.util

import android.annotation.SuppressLint
import timber.log.Timber

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

const val LOG_TAG: String = "APP_LOG"
const val LOG_TAG_OLD: String = "APP_LOG_OLD"

const val timePattern: String = "hh:mm:ss"

@SuppressLint("LogNotTimber")
private fun printLog(obj: Any, msg: String) {
    val sb = StringBuilder()
        .append("{")
        .append(obj.getFormatClassName())
        .append("}, msg: ")
        .append(msg)

    Timber.tag(LOG_TAG).i(sb.toString())
//    Log.i(LOG_TAG_OLD, sb.toString())
}

@Deprecated("Logger")
fun Any.toLog(msg: String) {
    printLog(this, msg)
}

@Deprecated("Logger")
fun Any.toLog(msg: String, params: Map<String, Any?>) {
    val list = params.map {
        "{${it.key}=${it.value}}"
    }
    printLog(this, "$msg - $list")
}

@Deprecated("Logger")
fun Any.toLog(msg: String, param: String, value: Any?) {
    printLog(this, "$msg - {[$param=$value]}")
}

private fun Any.getFormatClassName(): String {
    return "${this::class.java.simpleName}.class"
}
package ua.notky.base.extension

import android.net.Uri

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

const val HTTP = "http://"
const val HTTPS = "https://"

fun String?.toWebUri(): Uri {
    return this?.let {
        if(!it.startsWith(HTTP) && !it.startsWith(HTTPS)) {
            Uri.parse(HTTPS + it)
        } else {
            Uri.parse(it)
        }
    } ?: Uri.parse("")
}
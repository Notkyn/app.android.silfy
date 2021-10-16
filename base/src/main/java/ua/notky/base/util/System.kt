package ua.notky.base.util

import android.os.Build

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

fun checkVersionSDK(version: Int): Boolean {
    return Build.VERSION.SDK_INT >= version
}
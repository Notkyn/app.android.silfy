package ua.notky.base.extension

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

const val RECORD_AUDIO_PERMISSION = Manifest.permission.RECORD_AUDIO

fun Context.checkPermission(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) ==
            PackageManager.PERMISSION_GRANTED
}
package ua.notky.base.extension

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

inline fun <reified T : Activity> Context.startActivity(block: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java).apply(block)
    startActivity(intent)
}

fun Context.toast(message: CharSequence, length: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, message, length).show()

fun Context.copyToClipboard(text: CharSequence?) {
    if (text.isNullOrBlank())
        return

    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("ClipData", text)
    clipboard.setPrimaryClip(clip)
}

fun Context.openAppSystemSettings() {
    startActivity(
        Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", packageName, null)
        }
    )
}

fun Context.getLocalizeString(resId: Int): String {
    return this.resources.getString(resId)
}
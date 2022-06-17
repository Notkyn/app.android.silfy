package ua.notky.base.extension

import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import android.os.PowerManager
import android.os.Vibrator
import android.os.VibratorManager

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

fun Context.getVibrateService(): Vibrator {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager =
            this.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }
}

fun Context.getKeyguardManagerService(): KeyguardManager {
    return this.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
}

fun Context.getPowerManagerService(): PowerManager {
    return this.getSystemService(Context.POWER_SERVICE) as PowerManager
}
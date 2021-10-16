package ua.notky.base.extension

import android.app.KeyguardManager
import android.content.Context
import android.os.PowerManager
import android.os.Vibrator

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

fun Context.getVibrateService(): Vibrator {
    return this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
}

fun Context.getKeyguardManagerService(): KeyguardManager {
    return this.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
}

fun Context.getPowerManagerService(): PowerManager {
    return this.getSystemService(Context.POWER_SERVICE) as PowerManager
}
package ua.notky.silfy.ui.view

import android.app.Activity
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment

/**
 * Edge-to-edge helpers for Silfy 2.0 screens (targetSdk 36 always draws behind system bars on Android 15+).
 */
fun Activity.drawBehindSystemBars() {
    WindowCompat.setDecorFitsSystemWindows(window, false)
}

/** Light background → dark status / navigation bar icons, and vice versa */
fun Fragment.setLightSystemBars(light: Boolean) {
    val window = activity?.window ?: return
    WindowCompat.getInsetsController(window, window.decorView).apply {
        isAppearanceLightStatusBars = light
        isAppearanceLightNavigationBars = light
    }
}

/**
 * Adds system bar insets to the view's own padding. [bottom] also follows the keyboard,
 * so a button pinned to the bottom stays above it.
 */
fun View.applySystemBarsPadding(top: Boolean = false, bottom: Boolean = false) {
    val initialTop = paddingTop
    val initialBottom = paddingBottom

    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        val ime = insets.getInsets(WindowInsetsCompat.Type.ime())
        view.updatePadding(
            top = initialTop + if (top) bars.top else 0,
            bottom = initialBottom + if (bottom) maxOf(bars.bottom, ime.bottom) else 0
        )
        insets
    }
    ViewCompat.requestApplyInsets(this)
}

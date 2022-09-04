package ua.notky.base.extension

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import timber.log.Timber

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 15.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

private const val TITLE_CHOOSE_BROWSER = "Choose browser"

fun Fragment.openSafeScreen(actionId: Int) {
    try {
        this.findSafeNavController()?.navigate(actionId)
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

fun Fragment.openSafeScreen(navDirections: NavDirections, navOptions: NavOptions?) {
    try {
        this.findSafeNavController()?.navigate(navDirections, navOptions)
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

fun Fragment.openSafeScreen(navDirections: NavDirections) {
    this.openSafeScreen(navDirections, null)
}

fun Fragment.openSafePopBackstackScreen() {
    this.findSafeNavController()?.popBackStack()
}

fun Fragment.findSafeNavController(): NavController? {
    return try {
        findNavController()
    } catch (e: IllegalStateException) {
        e.printStackTrace()
        Timber.w("NavController hasn't been initialized")
        null
    }
}

fun Fragment.openLink(url: String?) {
    val intent = Intent(Intent.ACTION_VIEW, url.toWebUri())
    this.startActivity(Intent.createChooser(intent, TITLE_CHOOSE_BROWSER))
}

fun Fragment.onBackPressed(action: () -> Unit) {
    this.requireActivity().onBackPressedDispatcher.addCallback(this) {
        action.invoke()
    }
}

fun Fragment.openPlayMarket(packageName: String) {
    try {
        val link = "market://details?id=$packageName"
        val uri = Uri.parse(link)
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    } catch (e: ActivityNotFoundException) {
        val link = "https://play.google.com/store/apps/details?id=$packageName"
        val uri = Uri.parse(link)
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }
}


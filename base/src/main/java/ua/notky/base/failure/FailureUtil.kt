package ua.notky.base.failure

import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

fun Fragment.showFailure(msg: String?) {
    msg?.let {
        Toast.makeText(
            requireContext(),
            msg,
            Toast.LENGTH_LONG
        ).show()
    }
}
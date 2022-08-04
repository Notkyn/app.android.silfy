package ua.notky.silfy.extension

import android.app.Activity
import android.content.Context
import android.text.Spannable
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ua.notky.silfy.R

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 03.08.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

fun Activity.showAlert(
    title: String,
    message: String? = null,
    successButton: String? = null,
    cancelButton: String? = null,
    onSuccess: (() -> Unit)? = null,
    onCancel: (() -> Unit)? = null
) {
    showAlert(this, title, message, successButton, cancelButton, onSuccess, onCancel)
}

fun Fragment.showAlert(
    title: String,
    message: String? = null,
    successButton: String? = null,
    cancelButton: String? = null,
    onSuccess: (() -> Unit)? = null,
    onCancel: (() -> Unit)? = null
) {
    showAlert(
        this.requireContext(),
        title,
        message,
        successButton,
        cancelButton,
        onSuccess,
        onCancel
    )
}

fun Fragment.showAlert(
    title: Spannable,
    message: String? = null,
    successButton: String? = null,
    cancelButton: String? = null,
    onSuccess: (() -> Unit)? = null,
    onCancel: (() -> Unit)? = null
) {
    showAlert(
        this.requireContext(),
        title,
        message,
        successButton,
        cancelButton,
        onSuccess,
        onCancel
    )
}

private fun showAlert(
    context: Context,
    title: Spannable,
    message: String? = null,
    successButton: String? = null,
    cancelButton: String? = null,
    onSuccess: (() -> Unit)? = null,
    onCancel: (() -> Unit)? = null
) {
    val dialog = MaterialAlertDialogBuilder(context, R.style.CustomAlertDialogTheme)
    dialog.setTitle(title)
    message?.let { dialog.setMessage(it) }
    dialog.setNegativeButton(
        cancelButton ?: context.getString(R.string.button_cancel)
    ) { _, _ -> onCancel?.invoke() }
    dialog.setPositiveButton(
        successButton ?: context.getString(R.string.button_ok)
    ) { _, _ -> onSuccess?.invoke() }
    dialog.show()
}

private fun showAlert(
    context: Context,
    title: String,
    message: String? = null,
    successButton: String? = null,
    cancelButton: String? = null,
    onSuccess: (() -> Unit)? = null,
    onCancel: (() -> Unit)? = null
) {
    val dialog = MaterialAlertDialogBuilder(context, R.style.CustomAlertDialogTheme)
    dialog.setTitle(title)
    message?.let { dialog.setMessage(it) }
    dialog.setNegativeButton(
        cancelButton ?: context.getString(R.string.button_cancel)
    ) { _, _ -> onCancel?.invoke() }
    dialog.setPositiveButton(
        successButton ?: context.getString(R.string.button_ok)
    ) { _, _ -> onSuccess?.invoke() }
    dialog.show()
}

fun Activity.showSimpleAlert(
    title: String,
    onSuccess: () -> Unit = {}
) {
    showSimpleAlert(this, title, onSuccess)
}

fun Fragment.showSimpleAlert(
    title: String,
    onSuccess: () -> Unit = {}
) {
    showSimpleAlert(this.requireContext(), title, onSuccess)
}

private fun showSimpleAlert(
    context: Context,
    title: String,
    onSuccess: () -> Unit = {}
) {
    MaterialAlertDialogBuilder(context, R.style.CustomAlertDialogTheme)
        .setTitle(title)
        .setPositiveButton(R.string.button_ok) { _, _ -> onSuccess.invoke() }
        .show()
}
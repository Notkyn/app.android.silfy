package ua.notky.base.ui.dialog.exstensions

import ua.notky.base.ui.dialog.common.BaseDialogFragment
import ua.notky.base.ui.dialog.listener.OnCancelDialogListener
import ua.notky.base.ui.dialog.listener.OnConfirmDialogListener

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

inline fun BaseDialogFragment.doOnConfirm(
    crossinline action: () -> Unit = {}
): OnConfirmDialogListener {
    val listener = object : OnConfirmDialogListener {
        override fun onConfirm() {
            action.invoke()
        }
    }

    setOnConfirmListener(listener)

    return listener
}

inline fun BaseDialogFragment.doOnCancel(
    crossinline action: () -> Unit = {}
): OnCancelDialogListener {
    val listener = object : OnCancelDialogListener {
        override fun onCancel() {
            action.invoke()
        }
    }

    setOnCancelListener(listener)

    return listener
}
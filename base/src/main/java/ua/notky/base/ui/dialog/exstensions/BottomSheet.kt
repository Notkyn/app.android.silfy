package ua.notky.base.ui.dialog.exstensions

import androidx.viewbinding.ViewBinding
import ua.notky.base.ui.dialog.bottomsheet.BaseBottomSheetDialogFragment
import ua.notky.base.ui.dialog.bottomsheet.BaseSelectBottomSheetDialogFragment
import ua.notky.base.ui.dialog.listener.OnCancelDialogListener
import ua.notky.base.ui.dialog.listener.OnConfirmDialogListener
import ua.notky.base.ui.dialog.listener.OnSelectDialogListener

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

inline fun BaseBottomSheetDialogFragment.doOnConfirm(
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

inline fun BaseBottomSheetDialogFragment.doOnCancel(
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

inline fun <T, VDB : ViewBinding> BaseSelectBottomSheetDialogFragment<T, VDB>.doOnSelect(
    crossinline action: (item: T) -> Unit = {}
): OnSelectDialogListener<T> {

    val listener = object : OnSelectDialogListener<T> {
        override fun onSelect(item: T) {
            action.invoke(item)
        }
    }

    setOnSelectListener(listener)

    return listener
}

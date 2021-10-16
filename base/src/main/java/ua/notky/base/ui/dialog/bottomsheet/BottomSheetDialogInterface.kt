package ua.notky.base.ui.dialog.bottomsheet

import android.content.DialogInterface
import ua.notky.base.ui.dialog.listener.OnCancelDialogListener
import ua.notky.base.ui.dialog.listener.OnConfirmDialogListener

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

interface BottomSheetDialogInterface {
    fun setOnShowListener(listener: DialogInterface.OnShowListener?)
    fun setOnDismissListener(listener: DialogInterface.OnDismissListener?)
    fun setOnConfirmListener(listener: OnConfirmDialogListener?)
    fun setOnCancelListener(listener: OnCancelDialogListener?)
}
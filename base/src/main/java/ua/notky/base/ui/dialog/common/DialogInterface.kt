package ua.notky.base.ui.dialog.common

import ua.notky.base.ui.dialog.listener.OnCancelDialogListener
import ua.notky.base.ui.dialog.listener.OnConfirmDialogListener

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

interface DialogInterface {
    fun setOnConfirmListener(listener: OnConfirmDialogListener?)
    fun setOnCancelListener(listener: OnCancelDialogListener?)
}
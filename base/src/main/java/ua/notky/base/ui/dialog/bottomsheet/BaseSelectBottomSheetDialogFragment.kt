package ua.notky.base.ui.dialog.bottomsheet

import androidx.viewbinding.ViewBinding
import ua.notky.base.ui.dialog.listener.OnSelectDialogListener

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

abstract class BaseSelectBottomSheetDialogFragment<T, VDB : ViewBinding>
    : BaseBindingBottomSheetDialogFragment<VDB>() {

    protected var mOnSelectListener: OnSelectDialogListener<T>? = null

    fun setOnSelectListener(listener: OnSelectDialogListener<T>) {
        mOnSelectListener = listener
    }
}
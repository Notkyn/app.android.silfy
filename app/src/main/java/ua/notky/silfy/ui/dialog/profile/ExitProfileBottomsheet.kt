package ua.notky.silfy.ui.dialog.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.dialog.bottomsheet.BaseBindingBottomSheetDialogFragment
import ua.notky.silfy.databinding.BottomsheetExitProfileBinding

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 21.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class ExitProfileBottomsheet : BaseBindingBottomSheetDialogFragment<BottomsheetExitProfileBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BottomsheetExitProfileBinding
        get() = BottomsheetExitProfileBinding::inflate

    override fun initializeListeners() {
        binding.buttonNo.setOnClickListener { onCancel() }
        binding.buttonYes.setOnClickListener { onConfirm() }
    }

    private fun onCancel() {
        dismiss()
    }

    private fun onConfirm() {
        mOnConfirmListener?.onConfirm()
        dismiss()
    }
}
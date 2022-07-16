package ua.notky.silfy.ui.dialog.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.dialog.bottomsheet.BaseBindingBottomSheetDialogFragment
import ua.notky.silfy.databinding.BottomsheetCreateNewProfileBinding

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class CreateNewProfileBottomsheet(
    val title: String?
) : BaseBindingBottomSheetDialogFragment<BottomsheetCreateNewProfileBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BottomsheetCreateNewProfileBinding
        get() = BottomsheetCreateNewProfileBinding::inflate

    override fun initializeViews() {
        binding.title = title
    }

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
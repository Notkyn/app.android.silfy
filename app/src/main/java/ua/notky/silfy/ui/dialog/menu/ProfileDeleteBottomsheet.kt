package ua.notky.silfy.ui.dialog.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.dialog.bottomsheet.BaseBindingBottomSheetDialogFragment
import ua.notky.silfy.databinding.BottomsheetDeleteProfileBinding
import ua.notky.silfy.models.model.Profile

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 28.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class ProfileDeleteBottomsheet(
    val profile: Profile
) : BaseBindingBottomSheetDialogFragment<BottomsheetDeleteProfileBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BottomsheetDeleteProfileBinding
        get() = BottomsheetDeleteProfileBinding::inflate

    override fun initializeViews() {
        binding.model = profile
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
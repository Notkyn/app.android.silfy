package ua.notky.silfy.ui.dialog.category

import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.dialog.bottomsheet.BaseBindingBottomSheetDialogFragment
import ua.notky.silfy.databinding.BotomsheetDeleteCategoryBinding

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 18.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class DeleteCategoryBottomsheet(
    private val title: String?
) : BaseBindingBottomSheetDialogFragment<BotomsheetDeleteCategoryBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BotomsheetDeleteCategoryBinding
        get() = BotomsheetDeleteCategoryBinding::inflate

    override fun initializeViews() {
        binding.title.text = title
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
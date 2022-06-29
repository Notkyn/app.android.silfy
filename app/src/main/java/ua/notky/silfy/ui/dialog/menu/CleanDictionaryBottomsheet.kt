package ua.notky.silfy.ui.dialog.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.dialog.bottomsheet.BaseBindingBottomSheetDialogFragment
import ua.notky.silfy.databinding.BottomsheetCleanDictionaryBinding
import ua.notky.silfy.models.enums.DictionaryCardType

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 29.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class CleanDictionaryBottomsheet(
    val type: DictionaryCardType
) : BaseBindingBottomSheetDialogFragment<BottomsheetCleanDictionaryBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BottomsheetCleanDictionaryBinding
        get() = BottomsheetCleanDictionaryBinding::inflate

    override fun initializeViews() {
        binding.title.text = getString(type.dialog)
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
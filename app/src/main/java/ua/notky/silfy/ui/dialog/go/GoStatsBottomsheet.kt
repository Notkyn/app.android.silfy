package ua.notky.silfy.ui.dialog.go

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ua.notky.base.ui.dialog.bottomsheet.BaseBindingBottomSheetDialogFragment
import ua.notky.base.util.toLog
import ua.notky.silfy.databinding.BottomsheetGoStatsBinding
import ua.notky.silfy.models.enums.GoStatsType
import ua.notky.silfy.viewmodel.go.GoViewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 11.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class GoStatsBottomsheet(val type: GoStatsType) :
    BaseBindingBottomSheetDialogFragment<BottomsheetGoStatsBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BottomsheetGoStatsBinding
        get() = BottomsheetGoStatsBinding::inflate

    private val goViewModel by activityViewModels<GoViewModel>()

    override fun initializeViews() {
        setDialogState(BottomSheetBehavior.STATE_EXPANDED)
        binding.type = type
        binding.model = goViewModel.stats
    }

    override fun initializeListeners() {
        binding.buttonOk.setOnClickListener { onOkClick() }

        setOnDismissListener { onOkClick() }
    }

    private fun onOkClick() {
        mOnConfirmListener?.onConfirm()
        dismiss()
    }


}
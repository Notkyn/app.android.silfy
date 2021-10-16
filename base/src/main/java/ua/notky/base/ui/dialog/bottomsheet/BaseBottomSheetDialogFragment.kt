package ua.notky.base.ui.dialog.bottomsheet

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import ua.notky.base.ui.dialog.listener.OnCancelDialogListener
import ua.notky.base.ui.dialog.listener.OnConfirmDialogListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ua.notky.base.R
import ua.notky.base.changeable.ActionMode
import ua.notky.base.changeable.ValidationError
import ua.notky.base.failure.Failure
import ua.notky.base.failure.showFailure
import ua.notky.base.viewmodel.ViewModelSet

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment(),
    BottomSheetDialogInterface {

    private var dialogState: Int? = null
    protected var mOnConfirmListener: OnConfirmDialogListener? = null
    protected var mOnCancelListener: OnCancelDialogListener? = null

    override fun setOnConfirmListener(listener: OnConfirmDialogListener?) {
        mOnConfirmListener = listener
    }

    override fun setOnCancelListener(listener: OnCancelDialogListener?) {
        mOnCancelListener = listener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initViewModels()
        initListeners()
        setOnShowListener(null)
        setObserveToBaseViewModels()
        init()
    }

    override fun setOnShowListener(listener: DialogInterface.OnShowListener?) {
        dialog?.setOnShowListener {
            listener?.onShow(it)
            initStateDialog(it)
        }
    }

    override fun setOnDismissListener(listener: DialogInterface.OnDismissListener?){
        dialog?.setOnDismissListener(listener)
    }
    private fun initStateDialog(dialogInterface: DialogInterface) {
        val bottomSheetDialog = dialogInterface as BottomSheetDialog
        val bottomSheetInternal = bottomSheetDialog.findViewById<View>(
            com.google.android.material.R.id.design_bottom_sheet
        )
        dialogState?.let {
            if (bottomSheetInternal != null) {
                BottomSheetBehavior.from(bottomSheetInternal).state = it
            }
        }
    }

    /**
     * Sets the display state of the dialog
     * If you need dialog with floating views
     * you must override the method: getTheme ()
     * Used BaseResizeBottomSheetDialogTheme for floating
     * or custom for other state
     * @param state: BottomSheetBehavior.STATE
     * BottomSheetBehavior.STATE_EXPANDED - fullscreen
     */
    protected fun setDialogState(state: Int) {
        this.dialogState = state
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BaseBottomSheetDialogTheme)
    }

    abstract fun init()

    protected open fun initViews() {}
    protected open fun initViewModels() {}
    protected open fun initListeners() {}

    protected open fun buildViewModels(): ViewModelSet {
        return ViewModelSet.Builder().build()
    }

    private fun setObserveToBaseViewModels() {
        val set = buildViewModels()

        set.viewModels.forEach { baseViewModel ->
            baseViewModel.init()
            baseViewModel.getFailure().observe(viewLifecycleOwner, { handleFailure(it) })
            baseViewModel.getActionMode().observe(viewLifecycleOwner, { handleActionMode(it) })
        }

        set.validationViewModels.forEach { baseValidationViewModel ->
            baseValidationViewModel.getValidationErrors().observe(
                viewLifecycleOwner,
                {
                    clearValidationErrors()
                    setValidationErrors(it)
                }
            )
        }
    }

    protected fun handleFailure(failure: Failure?) {
        failure?.localizeMsg?.let { showFailure(it) }
    }
    protected open fun handleActionMode(mode: ActionMode?) {}
    protected open fun setValidationErrors(errors: List<ValidationError>) {}
    protected open fun clearValidationErrors() {}
}

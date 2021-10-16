package ua.notky.base.ui.dialog.common

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import ua.notky.base.ui.dialog.listener.OnCancelDialogListener
import ua.notky.base.ui.dialog.listener.OnConfirmDialogListener
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

abstract class BaseDialogFragment : DialogFragment(), DialogInterface {

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
        setObserveToBaseViewModels()
        init()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.BaseDialogTheme)
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

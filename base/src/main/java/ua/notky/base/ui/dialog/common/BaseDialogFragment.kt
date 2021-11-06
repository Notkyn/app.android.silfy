package ua.notky.base.ui.dialog.common

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import ua.notky.base.R
import ua.notky.base.extension.subscribeToAllLiveDataFromBaseViewModels
import ua.notky.base.failure.Failure
import ua.notky.base.failure.showFailure
import ua.notky.base.ui.init.FailureHandler
import ua.notky.base.ui.init.ValidationErrorHandler
import ua.notky.base.ui.init.ViewModelActionHandler
import ua.notky.base.ui.init.ui.InitializationDialog
import ua.notky.base.ui.dialog.listener.OnCancelDialogListener
import ua.notky.base.ui.dialog.listener.OnConfirmDialogListener

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

abstract class BaseDialogFragment : DialogFragment(),
    DialogInterface,
    ViewModelActionHandler,
    InitializationDialog,
    ValidationErrorHandler,
    FailureHandler {

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
        init()
        initViews()
        initViewModels()
        initListeners()

        viewLifecycleOwner.subscribeToAllLiveDataFromBaseViewModels(
            buildViewModels(),
            this,
            this,
            this
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.BaseDialogTheme)
    }

    override fun handleFailure(failure: Failure) {
        failure.localizeMsg?.let { showFailure(it) }
    }
}

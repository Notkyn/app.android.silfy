package ua.notky.base.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ua.notky.base.extension.subscribeToAllLiveDataFromBaseViewModels
import ua.notky.base.failure.Failure
import ua.notky.base.failure.showFailure
import ua.notky.base.ui.init.FailureHandler
import ua.notky.base.ui.init.ValidationErrorHandler
import ua.notky.base.ui.init.ViewModelActionHandler
import ua.notky.base.ui.init.ui.InitializationFragment

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

abstract class BaseFragment : Fragment(),
    ViewModelActionHandler,
    InitializationFragment,
    ValidationErrorHandler,
    FailureHandler {

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

    override fun handleFailure(failure: Failure) {
        failure.localizeMsg?.let { showFailure(it) }
    }
}

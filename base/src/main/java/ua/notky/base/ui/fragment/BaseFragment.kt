package ua.notky.base.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ua.notky.base.extension.subscribeToAllLiveDataFromBaseViewModels
import ua.notky.base.ui.init.FailureHandler
import ua.notky.base.ui.init.ValidationHandler
import ua.notky.base.ui.init.ViewModelActionHandler
import ua.notky.base.ui.init.BaseInitialization

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

abstract class BaseFragment : Fragment(),
    ViewModelActionHandler,
    BaseInitialization,
    ValidationHandler,
    FailureHandler {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize(savedInstanceState)
        initializeViews()
        initializeViewModels()
        initializeListeners()
        initializeData()

        viewLifecycleOwner.subscribeToAllLiveDataFromBaseViewModels(
            injectViewModels(),
            this,
            this,
            this
        )
    }
}

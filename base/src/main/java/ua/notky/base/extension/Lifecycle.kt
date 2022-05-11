package ua.notky.base.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import ua.notky.base.ui.init.FailureHandler
import ua.notky.base.ui.init.ValidationErrorHandler
import ua.notky.base.ui.init.ViewModelActionHandler
import ua.notky.base.viewmodel.ViewModelSet

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 06.11.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

fun LifecycleOwner.subscribeToAllLiveDataFromBaseViewModels(
    viewModels: ViewModelSet,
    actionHandler: ViewModelActionHandler,
    validationHandler: ValidationErrorHandler,
    failureHandler: FailureHandler
) {
    viewModels.viewModels.forEach { baseViewModel ->
        baseViewModel.init()

        baseViewModel.getFailure()?.let { liveData ->
            liveData.observe(this) { failure ->
                failure?.let { failureHandler.handleFailure(it) }
            }
        }

        baseViewModel.getAction().observe(this) { action ->
            action?.let { actionHandler.handleActionVM(it.type) }
        }
    }

    viewModels.validationViewModels.forEach { baseValidationViewModel ->
        baseValidationViewModel.getValidationErrors().observe(
            this
        ) {
            validationHandler.clearValidationErrors()
            validationHandler.setValidationErrors(it)
        }
    }
}

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))

package ua.notky.base.viewmodel

import ua.notky.base.ui.init.viewmodel.InitializationValidationViewModel
import ua.notky.base.ui.init.viewmodel.InitializationViewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

class ViewModelSet (
    val viewModels: Set<InitializationViewModel>,
    val validationViewModels: Set<InitializationValidationViewModel>
) {

    data class Builder(
        private val _viewModels: MutableSet<InitializationViewModel> = mutableSetOf(),
        private val _validationViewModels: MutableSet<InitializationValidationViewModel> = mutableSetOf()
    ) {
        fun addViewModel(viewModel: InitializationViewModel) = apply {
            _viewModels.add(viewModel)
        }

        fun addValidationViewModel(viewModel: InitializationValidationViewModel) = apply {
            _validationViewModels.add(viewModel)
        }

        fun build(): ViewModelSet {
            return ViewModelSet(_viewModels, _validationViewModels)
        }
    }
}
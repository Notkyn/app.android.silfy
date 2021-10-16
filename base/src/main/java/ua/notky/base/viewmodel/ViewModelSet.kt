package ua.notky.base.viewmodel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

class ViewModelSet (
    val viewModels: Set<BaseViewModel>,
    val validationViewModels: Set<BaseValidationViewModel>
) {

    data class Builder(
        private val _viewModels: MutableSet<BaseViewModel> = mutableSetOf(),
        private val _validationViewModels: MutableSet<BaseValidationViewModel> = mutableSetOf()
    ) {
        fun addViewModel(viewModel: BaseViewModel) = apply {
            _viewModels.add(viewModel)
        }

        fun addValidationViewModel(viewModel: BaseValidationViewModel) = apply {
            _validationViewModels.add(viewModel)
        }

        fun build(): ViewModelSet {
            return ViewModelSet(_viewModels, _validationViewModels)
        }
    }
}
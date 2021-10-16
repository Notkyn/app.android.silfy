package ua.notky.base.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
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

abstract class BaseFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        initViews()
        initViewModels()
        initListeners()
        setObserveToBaseViewModels()
    }

    abstract fun init()

    protected open fun initViews() {}
    protected open fun initViewModels() {}
    protected open fun initListeners() {}

    //  this method need override for building viewModels dependencies
    protected abstract fun buildViewModels(): ViewModelSet

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

    protected open fun handleFailure(failure: Failure?) {
        failure?.localizeMsg?.let { showFailure(it) }
    }

    // Override method if you want catch any action from viewModels
    protected open fun handleActionMode(mode: ActionMode?) {}
    // Override method if you want set validations errors to you views
    protected open fun setValidationErrors(errors: List<ValidationError>) {}
    protected open fun clearValidationErrors() {}
}

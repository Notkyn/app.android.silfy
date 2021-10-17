package ua.notky.silfy.ui.fragment.auth

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ua.notky.base.changeable.ActionMode
import ua.notky.base.changeable.ValidationError
import ua.notky.base.extension.clearError
import ua.notky.base.extension.setErrorMsg
import ua.notky.base.extension.toast
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.R
import ua.notky.silfy.databinding.FragmentAuthBinding
import ua.notky.silfy.viewmodel.auth.AuthViewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

class AuthFragment : BaseBindingFragment<FragmentAuthBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAuthBinding
        get() = FragmentAuthBinding::inflate

    private val authViewModel by activityViewModels<AuthViewModel>()

    override fun init() {}

    override fun buildViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(authViewModel)
            .addValidationViewModel(authViewModel)
            .build()
    }

    override fun initViews() {
        binding.model = authViewModel.getEmptyModel()

        binding.editEmail.setTargetForCleanFocus(binding.inputEmail)
    }

    override fun initListeners() {
        binding.buttonContinue.setOnClickListener {
            authViewModel.onContinue()
        }
    }

    override fun handleActionMode(mode: ActionMode?) {
        if(mode is ActionMode.Navigate.ToMain) {
            context?.toast("Go to main application")
        }
    }

    override fun setValidationErrors(errors: List<ValidationError>) {
        errors.forEach {
            if(it is ValidationError.Email) binding.inputEmail.setErrorMsg(it.msg)
        }
    }

    override fun clearValidationErrors() {
        binding.inputEmail.clearError()
    }
}
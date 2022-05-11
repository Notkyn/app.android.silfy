package ua.notky.silfy.ui.fragment.auth

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ua.notky.base.extension.startActivity
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.validation.ValidationError
import ua.notky.base.validation.clearError
import ua.notky.base.validation.setErrorMsg
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.config.ACTION_TO_MAIN
import ua.notky.silfy.config.VALIDATION_EMAIL
import ua.notky.silfy.databinding.FragmentAuthBinding
import ua.notky.silfy.ui.activity.MainActivity
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

    override fun initialize() {}

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(authViewModel)
            .addValidationViewModel(authViewModel)
            .build()
    }

    override fun initializeViews() {
        binding.model = authViewModel.getEmptyModel()

        binding.editEmail.setTargetForCleanFocus(binding.inputEmail)
    }

    override fun initializeListeners() {
        binding.buttonContinue.setOnClickListener {
            authViewModel.onContinue()
        }
    }

    override fun handleActionVM(type: Int) {
        when(type) {
            ACTION_TO_MAIN -> goToNextApplication()
        }
    }

    override fun setValidationErrors(errors: List<ValidationError>) {
        errors.forEach {
            when(it.type) {
                VALIDATION_EMAIL -> binding.inputEmail.setErrorMsg(it.msg)
            }
        }
    }

    override fun clearValidationErrors() {
        binding.inputEmail.clearError()
    }

    private fun goToNextApplication() {
        activity?.startActivity<MainActivity>()
        activity?.finishAffinity()
    }
}
package ua.notky.silfy.ui.fragment.auth

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ua.notky.base.extension.observe
import ua.notky.base.extension.openSafeScreen
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.validation.ValidationError
import ua.notky.base.validation.clearError
import ua.notky.base.validation.setErrorMsg
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.R
import ua.notky.silfy.config.VALIDATION_PROFILE_NAME
import ua.notky.silfy.databinding.FragmentAuthBinding
import ua.notky.silfy.extension.showSimpleAlert
import ua.notky.silfy.models.model.Profile
import ua.notky.silfy.models.states.AuthUiState
import ua.notky.silfy.ui.dialog.profile.MoreProfileBottomsheet
import ua.notky.silfy.viewmodel.StateViewModel
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
    private val stateViewModel by activityViewModels<StateViewModel>()

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(authViewModel)
            .addValidationViewModel(authViewModel)
            .build()
    }

    override fun initializeViews() {
        binding.model = authViewModel.getEmptyModel()
        binding.state = stateViewModel.state

        binding.editName.setTargetForCleanFocus(binding.labelName)
    }

    override fun initializeListeners() {
        binding.buttonContinue.setOnClickListener { authViewModel.onContinue() }
        binding.buttonMoreProfiles.setOnClickListener { showMoreProfilesDialog() }
    }

    override fun initializeViewModels() {
        observe(authViewModel.profileState, ::renderAuthUiState)
        observe(authViewModel.profiles, ::renderProfiles)
    }

    private fun renderAuthUiState(state: AuthUiState?) {
        stateViewModel.setLoading(state == AuthUiState.Loading)

        when (state) {
            AuthUiState.Loaded -> goToNextApplication()
            AuthUiState.Created -> goNextHelp()
            AuthUiState.Failure.Missing -> showSimpleAlert(getString(R.string.alert_error_auth_missing_profile))
            AuthUiState.Failure.ErrorCreate -> showSimpleAlert(getString(R.string.alert_error_auth_create_profile))
            else -> {}
        }
    }

    private fun renderProfiles(profiles: List<Profile>?) {
        stateViewModel.checkMoreProfiles(profiles)
    }

    private fun showMoreProfilesDialog() {
        val dialog = MoreProfileBottomsheet()
        dialog.show(parentFragmentManager, dialog::class.java.simpleName)
    }

    override fun setValidationErrors(errors: List<ValidationError>) {
        errors.forEach {
            when (it.type) {
                VALIDATION_PROFILE_NAME -> binding.inputName.setErrorMsg(it.msg)
            }
        }
    }

    override fun clearValidationErrors() {
        binding.inputName.clearError()
    }

    private fun goToNextApplication() {
        openSafeScreen(AuthFragmentDirections.toActivityMain())
        activity?.finishAffinity()
    }

    private fun goNextHelp() {
        openSafeScreen(AuthFragmentDirections.toFragmentInfo())
    }
}
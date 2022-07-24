package ua.notky.silfy.ui.dialog.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ua.notky.base.extension.observe
import ua.notky.base.extension.toast
import ua.notky.base.ui.dialog.bottomsheet.BaseBindingBottomSheetDialogFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.R
import ua.notky.silfy.databinding.BottomsheetEditProfileBinding
import ua.notky.silfy.models.states.UpdateProfileUiState
import ua.notky.silfy.viewmodel.StateViewModel
import ua.notky.silfy.viewmodel.profile.EditProfileViewModel
import ua.notky.silfy.viewmodel.profile.ProfileViewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 22.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class EditProfileBottomsheet :
    BaseBindingBottomSheetDialogFragment<BottomsheetEditProfileBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BottomsheetEditProfileBinding
        get() = BottomsheetEditProfileBinding::inflate

    private val editProfileViewModel by activityViewModels<EditProfileViewModel>()
    private val profileViewModel by activityViewModels<ProfileViewModel>()
    private val stateViewModel by activityViewModels<StateViewModel>()

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(editProfileViewModel)
            .build()
    }

    override fun initializeViews() {
        binding.model = editProfileViewModel.model
        binding.state = stateViewModel.state

        binding.editFirstname.setTargetForCleanFocus(binding.divider)
        binding.editFirstname.setNextTargetView(binding.editLastname)
        binding.editLastname.setTargetForCleanFocus(binding.divider)
    }

    override fun initializeViewModels() {
        editProfileViewModel.updateModel(
            profileViewModel.model.firstName.get(),
            profileViewModel.model.lastName.get()
        )

        observe(editProfileViewModel.updateState, ::renderUpdateState)
    }

    override fun initializeListeners() {
        binding.buttonSave.setOnClickListener { onSave() }
    }

    private fun renderUpdateState(state: UpdateProfileUiState?) {
        stateViewModel.setLoading(state == UpdateProfileUiState.Updating)

        when (state) {
            UpdateProfileUiState.Updated -> dismiss()
            UpdateProfileUiState.Failure.UpdateData -> toast(R.string.error_update_profile_data)
            else -> {}
        }

        editProfileViewModel.clearState()
    }

    private fun onSave() {
        editProfileViewModel.onSaveData()
    }
}
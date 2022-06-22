package ua.notky.silfy.ui.dialog.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ua.notky.base.ui.dialog.bottomsheet.BaseBindingBottomSheetDialogFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.databinding.BottomsheetEditProfileBinding
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

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(editProfileViewModel)
            .build()
    }

    override fun initializeViews() {
        binding.model = editProfileViewModel.model

        binding.editFirstname.setTargetForCleanFocus(binding.divider)
        binding.editFirstname.setNextTargetView(binding.editLastname)
        binding.editLastname.setTargetForCleanFocus(binding.divider)
    }

    override fun initializeViewModels() {
        editProfileViewModel.updateModel(
            profileViewModel.model.firstName.get(),
            profileViewModel.model.lastName.get()
        )
    }

    override fun initializeListeners() {
        binding.buttonSave.setOnClickListener { onSave() }
    }

    private fun onSave() {
        profileViewModel.updateModel(
            editProfileViewModel.model.firstName.get(),
            editProfileViewModel.model.lastName.get()
        )

        dismiss()
    }
}
package ua.notky.silfy.ui.dialog.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import ua.notky.base.extension.observe
import ua.notky.base.ui.dialog.bottomsheet.BaseBindingBottomSheetDialogFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.R
import ua.notky.silfy.databinding.BottomsheetEditProfilePhotoBinding
import ua.notky.silfy.models.states.UpdateProfileUiState
import ua.notky.silfy.extension.showSimpleAlert
import ua.notky.silfy.viewmodel.StateViewModel
import ua.notky.silfy.viewmodel.profile.EditProfileViewModel
import ua.notky.silfy.viewmodel.profile.ProfileViewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@AndroidEntryPoint
class EditProfilePhotoBottomsheet :
    BaseBindingBottomSheetDialogFragment<BottomsheetEditProfilePhotoBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BottomsheetEditProfilePhotoBinding
        get() = BottomsheetEditProfilePhotoBinding::inflate

    private val editProfileViewModel by viewModels<EditProfileViewModel>()
    private val profileViewModel by activityViewModels<ProfileViewModel>()
    private val stateViewModel by activityViewModels<StateViewModel>()

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(editProfileViewModel)
            .build()
    }

    override fun initializeViews() {
        setDialogState(BottomSheetBehavior.STATE_EXPANDED)
        binding.model = editProfileViewModel.model
        binding.state = stateViewModel.state
    }

    override fun initializeViewModels() {
        editProfileViewModel.updatePhoto(profileViewModel.profile.value?.photo)

        observe(editProfileViewModel.updateState, ::renderUpdateState)
    }

    override fun initializeListeners() {
        binding.buttonSave.setOnClickListener { onSave() }
        binding.buttonChange.setOnClickListener { onChange() }
        binding.imageAvatar.setOnClickListener { onChange() }
    }

    private fun renderUpdateState(state: UpdateProfileUiState?) {
        stateViewModel.setLoading(state == UpdateProfileUiState.Updating)

        when (state) {
            UpdateProfileUiState.Updated -> dismiss()
            UpdateProfileUiState.Failure.UpdateData -> showSimpleAlert(getString(R.string.alert_error_update_profile_data))
            else -> {}
        }

        editProfileViewModel.clearState()
    }

    private fun onSave() {
        editProfileViewModel.saveImage()
    }

    private fun onChange() {
        getImageContent.launch(MEDIA_TYPE_IMAGE)
    }

    private val getImageContent = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri -> uri?.let { editProfileViewModel.loadImage(it) } }

    companion object {
        private const val MEDIA_TYPE_IMAGE = "image/*"
    }
}
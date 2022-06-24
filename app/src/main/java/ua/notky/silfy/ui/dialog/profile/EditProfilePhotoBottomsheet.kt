package ua.notky.silfy.ui.dialog.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ua.notky.base.extension.observe
import ua.notky.base.ui.dialog.bottomsheet.BaseBindingBottomSheetDialogFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.databinding.BottomsheetEditProfilePhotoBinding
import ua.notky.silfy.viewmodel.profile.EditProfileViewModel
import ua.notky.silfy.viewmodel.profile.ProfileViewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class EditProfilePhotoBottomsheet :
    BaseBindingBottomSheetDialogFragment<BottomsheetEditProfilePhotoBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BottomsheetEditProfilePhotoBinding
        get() = BottomsheetEditProfilePhotoBinding::inflate

    private val editProfileViewModel by viewModels<EditProfileViewModel>()
    private val profileViewModel by activityViewModels<ProfileViewModel>()

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(editProfileViewModel)
            .build()
    }

    override fun initializeViews() {
        setDialogState(BottomSheetBehavior.STATE_EXPANDED)
        binding.model = editProfileViewModel.model
    }

    override fun initializeViewModels() {
        editProfileViewModel.updatePhoto(profileViewModel.model.avatar.get())

        observe(editProfileViewModel.imagePath, ::renderImagePath)
    }

    override fun initializeListeners() {
        binding.buttonSave.setOnClickListener { onSave() }
        binding.buttonChange.setOnClickListener { onChange() }
    }

    private fun onSave() {
        editProfileViewModel.saveImage()
    }

    private fun onChange() {
        getImageContent.launch(MEDIA_TYPE_IMAGE)
    }

    private fun renderImagePath(path: String?) {
        path?.let { profileViewModel.updatePhoto(it) }
        dismiss()
    }

    private val getImageContent = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { editProfileViewModel.loadImage(requireContext(), it) }

    companion object {
        private const val MEDIA_TYPE_IMAGE = "image/*"
    }
}
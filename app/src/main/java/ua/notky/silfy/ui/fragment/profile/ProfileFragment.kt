package ua.notky.silfy.ui.fragment.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ua.notky.base.extension.observe
import ua.notky.base.extension.openSafeScreen
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.R
import ua.notky.silfy.databinding.FragmentProfileBinding
import ua.notky.silfy.extension.showAlert
import ua.notky.silfy.models.model.Profile
import ua.notky.silfy.ui.dialog.profile.EditProfileBottomsheet
import ua.notky.silfy.ui.dialog.profile.EditProfilePhotoBottomsheet
import ua.notky.silfy.viewmodel.profile.ProfileViewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class ProfileFragment : BaseBindingFragment<FragmentProfileBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileBinding
        get() = FragmentProfileBinding::inflate

    private val profileViewModel by activityViewModels<ProfileViewModel>()

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(profileViewModel)
            .build()
    }

    override fun initializeViews() {
        binding.model = profileViewModel.model
    }

    override fun initializeListeners() {
        binding.header.handleEditClick { showEditDialog() }
        binding.header.handleEditPhotoClick { showEditPhotoDialog() }
        binding.buttonExit.setOnClickListener { showExitProfileAlert() }
    }

    override fun initializeViewModels() {
        observe(profileViewModel.profile, ::renderProfile)
        observe(profileViewModel.logout, ::renderLogoutState)
    }

    override fun initializeData() {
        profileViewModel.fetchCurrentProfile()
    }

    private fun renderProfile(profile: Profile?) {
        profile?.let { profileViewModel.updateProfile(it) }
    }

    private fun showEditDialog() {
        val dialog = EditProfileBottomsheet()

        dialog.show(parentFragmentManager, dialog::class.java.simpleName)
    }

    private fun showEditPhotoDialog() {
        val dialog = EditProfilePhotoBottomsheet()

        dialog.show(parentFragmentManager, dialog::class.java.simpleName)
    }

    private fun showExitProfileAlert() {
        showAlert(
            title = getString(R.string.alert_title_exit_profile),
            successButton = getString(R.string.alert_button_exit),
            onSuccess = { profileViewModel.onLogout() }
        )
    }

    private fun renderLogoutState(state: Boolean?) {
        if (state == true) {
            onNextLoginScreen()
        }
    }

    private fun onNextLoginScreen() {
        openSafeScreen(ProfileFragmentDirections.toActivityAuth())
        activity?.finishAffinity()
    }
}
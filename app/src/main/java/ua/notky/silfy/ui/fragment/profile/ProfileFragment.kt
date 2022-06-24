package ua.notky.silfy.ui.fragment.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ua.notky.base.extension.startActivity
import ua.notky.base.ui.dialog.exstensions.doOnConfirm
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.databinding.FragmentProfileBinding
import ua.notky.silfy.ui.activity.AuthActivity
import ua.notky.silfy.ui.dialog.profile.EditProfileBottomsheet
import ua.notky.silfy.ui.dialog.profile.EditProfilePhotoBottomsheet
import ua.notky.silfy.ui.dialog.profile.ExitProfileBottomsheet
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
        binding.buttonExit.setOnClickListener { showExitDialog() }
    }

    override fun initializeData() {
        profileViewModel.fetchCurrentProfile()
    }

    private fun showEditDialog() {
        val dialog = EditProfileBottomsheet()

        dialog.show(parentFragmentManager, dialog::class.java.simpleName)
    }

    private fun showEditPhotoDialog() {
        val dialog = EditProfilePhotoBottomsheet()

        dialog.show(parentFragmentManager, dialog::class.java.simpleName)
    }

    private fun showExitDialog() {
        val dialog = ExitProfileBottomsheet()

        dialog.doOnConfirm { onNextLoginScreen() }

        dialog.show(parentFragmentManager, dialog::class.java.simpleName)
    }

    private fun onNextLoginScreen() {
        activity?.startActivity<AuthActivity>()
        activity?.finishAffinity()
    }
}
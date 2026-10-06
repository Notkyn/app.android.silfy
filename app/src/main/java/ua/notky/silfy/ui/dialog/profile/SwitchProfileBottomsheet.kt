package ua.notky.silfy.ui.dialog.profile

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ua.notky.base.extension.observe
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.R
import ua.notky.silfy.databinding.BottomsheetSwitchProfileBinding
import ua.notky.silfy.databinding.ItemProfileSwitchBinding
import ua.notky.silfy.models.model.Profile
import ua.notky.silfy.ui.activity.AuthActivity
import ua.notky.silfy.ui.activity.MainActivity
import ua.notky.silfy.ui.dialog.BaseSilfyBottomSheet
import ua.notky.silfy.ui.dialog.DialogTone
import ua.notky.silfy.ui.dialog.showSilfyDialog
import ua.notky.silfy.util.englishLanguageName
import ua.notky.silfy.viewmodel.profile.SwitchProfileViewModel
import ua.notky.silfy.viewmodel.profile.SwitchProfileViewModel.State

/**
 * 5c Switch profile. Another profile → the app restarts on its dictionary (in its language);
 * "New profile" → "Create profile", back returns here.
 */
@AndroidEntryPoint
class SwitchProfileBottomsheet : BaseSilfyBottomSheet<BottomsheetSwitchProfileBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BottomsheetSwitchProfileBinding
        get() = BottomsheetSwitchProfileBinding::inflate

    private val switchProfileViewModel by viewModels<SwitchProfileViewModel>()

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(switchProfileViewModel)
            .build()
    }

    override fun initializeListeners() {
        binding.rowNewProfile.setOnClickListener { goToNextCreateProfile() }
    }

    override fun initializeViewModels() {
        viewLifecycleOwner.observe(switchProfileViewModel.profiles) { renderProfiles() }
        viewLifecycleOwner.observe(switchProfileViewModel.activeId) { renderProfiles() }
        viewLifecycleOwner.observe(switchProfileViewModel.state, ::renderState)
    }

    private fun renderProfiles() {
        val profiles = switchProfileViewModel.profiles.value ?: return
        val activeId = switchProfileViewModel.activeId.value
        val inflater = LayoutInflater.from(requireContext())

        binding.listProfiles.removeAllViews()
        profiles.forEach { profile ->
            val row = ItemProfileSwitchBinding.inflate(inflater, binding.listProfiles, false)
            val isActive = profile.id == activeId

            row.avatar.setProfile(profile)
            row.textName.text = profile.name
            row.textLanguage.text = getString(
                R.string.session_direction,
                requireContext().englishLanguageName(),
                profile.language.nativeName
            )
            row.textActive.isVisible = isActive
            row.root.isActivated = isActive
            row.root.setOnClickListener { onProfileClick(profile, isActive) }

            binding.listProfiles.addView(row.root)
        }
    }

    private fun onProfileClick(profile: Profile, isActive: Boolean) {
        if (isActive) dismiss() else switchProfileViewModel.switch(profile)
    }

    private fun renderState(state: State?) {
        when (state) {
            State.SWITCHED -> {
                switchProfileViewModel.consumeState()
                goToNextMain()
            }
            State.FAILURE -> {
                switchProfileViewModel.consumeState()
                showSilfyDialog(
                    icon = R.drawable.ic_lc_triangle_alert,
                    tone = DialogTone.WARNING,
                    title = getString(R.string.profile_error_switch),
                    message = getString(R.string.create_error_message),
                    cancelText = null
                )
            }
            else -> {}
        }
    }

    /** From scratch, so every screen reads the new profile; opens on the dictionary */
    private fun goToNextMain() {
        val intent = Intent(requireContext(), MainActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    private fun goToNextCreateProfile() {
        val intent = Intent(requireContext(), AuthActivity::class.java)
            .putExtra(AuthActivity.EXTRA_START, AuthActivity.START_CREATE)
        startActivity(intent)
        dismiss()
    }

    companion object {
        private const val TAG = "SwitchProfileBottomsheet"

        fun show(fragmentManager: FragmentManager) {
            if (fragmentManager.findFragmentByTag(TAG) != null) return
            SwitchProfileBottomsheet().show(fragmentManager, TAG)
        }
    }
}

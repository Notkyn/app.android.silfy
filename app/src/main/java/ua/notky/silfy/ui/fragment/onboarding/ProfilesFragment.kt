package ua.notky.silfy.ui.fragment.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ua.notky.base.extension.observe
import ua.notky.base.extension.openSafeScreen
import ua.notky.base.extension.startActivity
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.databinding.FragmentProfilesBinding
import ua.notky.silfy.models.model.Profile
import ua.notky.silfy.models.states.OnboardingUiState
import ua.notky.silfy.ui.activity.MainActivity
import ua.notky.silfy.ui.view.applySystemBarsPadding
import ua.notky.silfy.ui.view.setLightSystemBars
import ua.notky.silfy.viewmodel.onboarding.OnboardingViewModel

/** 1c Who's learning: pick a profile or create a new one. Without profiles opens "Create profile" */
class ProfilesFragment : BaseBindingFragment<FragmentProfilesBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfilesBinding
        get() = FragmentProfilesBinding::inflate

    private val onboardingViewModel by activityViewModels<OnboardingViewModel>()

    private val adapter = ProfilesAdapter { onboardingViewModel.selectProfile(it) }

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(onboardingViewModel)
            .build()
    }

    override fun onResume() {
        super.onResume()
        setLightSystemBars(true)
    }

    override fun initializeViews() {
        binding.root.applySystemBarsPadding(top = true, bottom = true)
        binding.listProfiles.adapter = adapter
    }

    override fun initializeListeners() {
        binding.buttonNewProfile.setOnClickListener {
            openSafeScreen(ProfilesFragmentDirections.toFragmentCreateProfile())
        }
    }

    override fun initializeViewModels() {
        observe(onboardingViewModel.profiles, ::renderProfiles)
        observe(onboardingViewModel.state, ::renderState)
    }

    private fun renderProfiles(profiles: List<Profile>?) {
        profiles ?: return

        if (profiles.isEmpty()) {
            openSafeScreen(ProfilesFragmentDirections.toFragmentCreateProfileFirst())
        } else {
            adapter.submitList(profiles)
        }
    }

    private fun renderState(state: OnboardingUiState?) {
        if (state == OnboardingUiState.ProfileSelected) {
            onboardingViewModel.consumeState()
            activity?.startActivity<MainActivity>()
            activity?.finishAffinity()
        }
    }
}

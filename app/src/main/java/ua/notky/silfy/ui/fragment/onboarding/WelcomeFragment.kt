package ua.notky.silfy.ui.fragment.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ua.notky.base.extension.openSafeScreen
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.databinding.FragmentWelcomeBinding
import ua.notky.silfy.ui.view.applySystemBarsPadding
import ua.notky.silfy.ui.view.setLightSystemBars
import ua.notky.silfy.viewmodel.onboarding.OnboardingViewModel

/** 1b Welcome: shown once, on the very first launch */
class WelcomeFragment : BaseBindingFragment<FragmentWelcomeBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentWelcomeBinding
        get() = FragmentWelcomeBinding::inflate

    private val onboardingViewModel by activityViewModels<OnboardingViewModel>()

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(onboardingViewModel)
            .build()
    }

    override fun onResume() {
        super.onResume()
        setLightSystemBars(false)
    }

    override fun initializeViews() {
        binding.top.applySystemBarsPadding(top = true)
        binding.panel.applySystemBarsPadding(bottom = true)
    }

    override fun initializeListeners() {
        binding.buttonStart.setOnClickListener {
            onboardingViewModel.onWelcomeShown()
            openSafeScreen(WelcomeFragmentDirections.toFragmentProfiles())
        }
    }
}

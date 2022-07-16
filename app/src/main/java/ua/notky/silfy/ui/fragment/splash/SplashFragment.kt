package ua.notky.silfy.ui.fragment.splash

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ua.notky.base.extension.observe
import ua.notky.base.extension.startActivity
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.databinding.FragmentSplashBinding
import ua.notky.silfy.ui.activity.AuthActivity
import ua.notky.silfy.ui.activity.MainActivity
import ua.notky.silfy.viewmodel.splash.SplashViewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class SplashFragment : BaseBindingFragment<FragmentSplashBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSplashBinding
        get() = FragmentSplashBinding::inflate

    private val splashVieModel by activityViewModels<SplashViewModel>()

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(splashVieModel)
            .build()
    }

    override fun initializeViews() {
        binding.viewModel = splashVieModel
    }

    override fun initializeViewModels() {
        observe(splashVieModel.loggedState, ::renderLoggedState)
    }

    private fun renderLoggedState(state: Boolean?) {
        when (state) {
            true -> goToNextApplication()
            false -> goToNextAuth()
            else -> {}
        }
    }

    private fun goToNextAuth() {
        activity?.startActivity<AuthActivity>()
        activity?.finishAffinity()
    }

    private fun goToNextApplication() {
        activity?.startActivity<MainActivity>()
        activity?.finishAffinity()
    }
}
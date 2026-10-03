package ua.notky.silfy.ui.fragment.splash

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ua.notky.base.extension.observe
import ua.notky.base.extension.openPlayMarket
import ua.notky.base.extension.startActivity
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.BuildConfig
import ua.notky.silfy.R
import ua.notky.silfy.databinding.FragmentSplashBinding
import ua.notky.silfy.extension.showAlert
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

    override fun onResume() {
        super.onResume()
        splashVieModel.checkAvailableUpdate()
    }

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
        observe(splashVieModel.readyUpdate, ::handleReadyUpdateState)
    }

    private fun renderLoggedState(state: Boolean?) {
        // Applying the profile language may recreate the activity: navigate only once
        if (activity?.isFinishing != false) return

        when (state) {
            true -> goToNextApplication()
            false -> goToNextAuth()
            else -> {}
        }
    }

    private fun handleReadyUpdateState(state: Boolean?) {
        state?.let {
            if(it) {
                showReadyUpdateAlert()
            } else {
                splashVieModel.checkLoggedUser()
            }
        }
    }

    private fun showReadyUpdateAlert() {
        showAlert(
            title = getString(R.string.alert_title_ready_update),
            successButton = getString(R.string.alert_button_yes),
            onSuccess = { openPlayMarket(BuildConfig.APPLICATION_ID) },
            cancelButton = getString(R.string.alert_button_no),
            onCancel = { splashVieModel.checkLoggedUser() },
            onDismiss = { splashVieModel.checkLoggedUser() }
        )
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
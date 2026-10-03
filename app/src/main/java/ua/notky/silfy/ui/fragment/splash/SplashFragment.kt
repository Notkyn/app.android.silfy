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
import ua.notky.silfy.models.states.StartRoute
import ua.notky.silfy.ui.activity.AuthActivity
import ua.notky.silfy.ui.activity.MainActivity
import ua.notky.silfy.ui.dialog.DialogTone
import ua.notky.silfy.ui.dialog.showSilfyDialog
import ua.notky.silfy.ui.view.applySystemBarsPadding
import ua.notky.silfy.ui.view.setLightSystemBars
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
        setLightSystemBars(false)
        splashVieModel.checkAvailableUpdate()
    }

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(splashVieModel)
            .build()
    }

    override fun initializeViews() {
        binding.root.applySystemBarsPadding(top = true, bottom = true)
    }

    override fun initializeViewModels() {
        observe(splashVieModel.route, ::renderRoute)
        observe(splashVieModel.readyUpdate, ::handleReadyUpdateState)
    }

    private fun renderRoute(route: StartRoute?) {
        // Applying the profile language may recreate the activity: navigate only once
        if (activity?.isFinishing != false) return

        when (route) {
            StartRoute.MAIN -> goToNextApplication()
            StartRoute.WELCOME -> goToNextAuth(AuthActivity.START_WELCOME)
            StartRoute.PROFILES -> goToNextAuth(AuthActivity.START_PROFILES)
            null -> {}
        }
    }

    private fun handleReadyUpdateState(state: Boolean?) {
        state?.let {
            if (it) {
                showReadyUpdateDialog()
            } else {
                splashVieModel.resolveRoute()
            }
        }
    }

    private fun showReadyUpdateDialog() {
        showSilfyDialog(
            icon = R.drawable.ic_lc_refresh_cw,
            tone = DialogTone.NEUTRAL,
            title = getString(R.string.update_title),
            message = getString(R.string.update_message),
            okText = getString(R.string.update_button),
            cancelText = getString(R.string.update_later),
            onOk = { openPlayMarket(BuildConfig.APPLICATION_ID) },
            onDismiss = { splashVieModel.resolveRoute() }
        )
    }

    private fun goToNextAuth(start: String) {
        activity?.startActivity<AuthActivity> { putExtra(AuthActivity.EXTRA_START, start) }
        activity?.finishAffinity()
    }

    private fun goToNextApplication() {
        activity?.startActivity<MainActivity>()
        activity?.finishAffinity()
    }
}

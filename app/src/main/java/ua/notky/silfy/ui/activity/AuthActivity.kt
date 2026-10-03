package ua.notky.silfy.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import dagger.hilt.android.AndroidEntryPoint
import ua.notky.base.ui.activity.BaseBindingActivity
import ua.notky.silfy.R
import ua.notky.silfy.databinding.ActivityAuthBinding
import ua.notky.silfy.ui.view.drawBehindSystemBars

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

/**
 * Onboarding: Welcome → Who's learning → Create profile → Good to know.
 * [EXTRA_START] picks the first screen (Who's learning by default).
 */
@AndroidEntryPoint
class AuthActivity : BaseBindingActivity<ActivityAuthBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityAuthBinding
        get() = ActivityAuthBinding::inflate

    override fun setNavController(): Int {
        return R.id.nav_host_fragment
    }

    override fun initialize(savedInstanceState: Bundle?) {
        super.initialize(savedInstanceState)
        drawBehindSystemBars()
    }

    override fun onInitNavController() {
        val graph = mNavController.navInflater.inflate(R.navigation.nav_graph_auth)
        graph.setStartDestination(
            when (intent.getStringExtra(EXTRA_START)) {
                START_WELCOME -> R.id.fragment_welcome
                else -> R.id.fragment_profiles
            }
        )
        // On recreation (e.g. the app language changed) setGraph restores the back stack
        mNavController.setGraph(graph, null)
    }

    companion object {
        const val EXTRA_START = "start"
        const val START_WELCOME = "welcome"
        const val START_PROFILES = "profiles"
    }
}

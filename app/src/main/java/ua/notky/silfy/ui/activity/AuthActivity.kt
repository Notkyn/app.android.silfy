package ua.notky.silfy.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import ua.notky.base.ui.activity.BaseBindingActivity
import ua.notky.silfy.R
import ua.notky.silfy.databinding.ActivityAuthBinding

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
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
        window.statusBarColor = ContextCompat.getColor(this, R.color.primary_color)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.primary_color)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        if (mNavController.currentDestination?.id == R.id.fragment_auth) {
            finish()
        }
    }
}
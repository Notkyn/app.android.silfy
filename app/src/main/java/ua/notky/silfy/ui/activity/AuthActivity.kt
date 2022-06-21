package ua.notky.silfy.ui.activity

import android.os.Bundle
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import ua.notky.base.ui.activity.BaseActivity
import ua.notky.base.util.toLog
import ua.notky.silfy.R

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@AndroidEntryPoint
class AuthActivity : BaseActivity() {

    override fun initialize(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_auth)
        window.statusBarColor = ContextCompat.getColor(this, R.color.primary_color)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.primary_color)
    }

    override fun onBackPressed() {
        finish()
    }
}
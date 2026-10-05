package ua.notky.silfy.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import dagger.hilt.android.AndroidEntryPoint
import ua.notky.base.ui.activity.BaseBindingActivity
import ua.notky.silfy.databinding.ActivityGoBinding
import ua.notky.silfy.ui.view.drawBehindSystemBars

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

/** Training (Start in the bottom navigation): 4a settings → 4c–4i session → 4j results */
@AndroidEntryPoint
class GoActivity : BaseBindingActivity<ActivityGoBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityGoBinding
        get() = ActivityGoBinding::inflate

    override fun setNavController(): Int {
        return binding.navHostFragment.id
    }

    override fun initialize(savedInstanceState: Bundle?) {
        super.initialize(savedInstanceState)
        drawBehindSystemBars()
    }

    /** ✕ on the settings, or the session cannot go on */
    fun close() {
        finish()
        overridePendingTransition(android.R.anim.fade_in, ua.notky.base.R.anim.slide_out_bottom)
    }

    /** "Back to dictionary": the Words tab of the main screen */
    fun backToDictionary() {
        startActivity(
            Intent(this, MainActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                .putExtra(MainActivity.EXTRA_OPEN_WORDS, true)
        )
        close()
    }
}

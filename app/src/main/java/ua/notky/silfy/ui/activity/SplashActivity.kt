package ua.notky.silfy.ui.activity

import android.os.Bundle
import ua.notky.base.extension.setFullscreenMode
import ua.notky.base.ui.activity.BaseActivity
import ua.notky.silfy.R

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class SplashActivity : BaseActivity() {

    override fun init(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_splash)
        window.setFullscreenMode()
    }

//    @Deprecated("delay for test")
//    private fun onNextPage() {
//        CoroutineScope(Dispatchers.Main).launch {
//            delay(1000)
//            startActivity<AuthActivity>()
//            finish()
//        }
//    }
}
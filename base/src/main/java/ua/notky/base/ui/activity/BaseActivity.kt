package ua.notky.base.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initViewModels()
        initListeners()

        init(savedInstanceState)
    }

    abstract fun init(savedInstanceState: Bundle?)

    protected open fun initViews() {}
    protected open fun initViewModels() {}
    protected open fun initListeners() {}
}
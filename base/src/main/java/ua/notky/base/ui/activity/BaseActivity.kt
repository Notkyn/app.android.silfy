package ua.notky.base.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ua.notky.base.extension.subscribeToAllLiveDataFromBaseViewModels
import ua.notky.base.ui.init.FailureHandler
import ua.notky.base.ui.init.ValidationErrorHandler
import ua.notky.base.ui.init.ViewModelActionHandler
import ua.notky.base.ui.init.ui.InitializationActivity

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

abstract class BaseActivity : AppCompatActivity(),
    ViewModelActionHandler,
    InitializationActivity,
    ValidationErrorHandler,
    FailureHandler {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init(savedInstanceState)

        initViews()
        initViewModels()
        initListeners()

        this.subscribeToAllLiveDataFromBaseViewModels(
            buildViewModels(),
            this,
            this,
            this
        )
    }
}
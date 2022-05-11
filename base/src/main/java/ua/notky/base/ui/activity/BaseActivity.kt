package ua.notky.base.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ua.notky.base.extension.subscribeToAllLiveDataFromBaseViewModels
import ua.notky.base.ui.init.FailureHandler
import ua.notky.base.ui.init.ValidationHandler
import ua.notky.base.ui.init.ViewModelActionHandler
import ua.notky.base.ui.init.BaseInitialization

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

abstract class BaseActivity : AppCompatActivity(),
    ViewModelActionHandler,
    BaseInitialization,
    ValidationHandler,
    FailureHandler {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize(savedInstanceState)

        initializeViews()
        initializeViewModels()
        initializeListeners()
        initializeData()

        this.subscribeToAllLiveDataFromBaseViewModels(
            injectViewModels(),
            this,
            this,
            this
        )
    }
}
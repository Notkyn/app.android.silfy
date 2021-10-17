package ua.notky.base.ui.activity

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

abstract class BaseNavigationActivity : BaseActivity(), NavigationActivity {

    protected lateinit var mNavController: NavController
    protected lateinit var mNavHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initNavController()
    }

    abstract fun setNavController(): Int?


    override fun initNavController() {
        setNavController()?.let {
            @IdRes val resIdNavHostFragment = it

                if (supportFragmentManager.findFragmentById(resIdNavHostFragment) is NavHostFragment) {
                    mNavHostFragment =
                        supportFragmentManager.findFragmentById(resIdNavHostFragment) as NavHostFragment
                    mNavController = mNavHostFragment.navController

                    onInitNavController()
                }
        }
    }

    /**
     * called when initialization of nav controller was successful
     * you can do anything with NavController and don't get any exceptions
     */
    open fun onInitNavController() {
        // empty
    }
}
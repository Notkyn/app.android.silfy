package ua.notky.silfy.ui.activity

import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.navigation.NavOptions
import dagger.hilt.android.AndroidEntryPoint
import ua.notky.base.ui.activity.BaseBindingActivity
import ua.notky.silfy.R
import ua.notky.silfy.databinding.ActivityMainBinding
import ua.notky.silfy.viewmodel.words.WordsViewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@AndroidEntryPoint
class MainActivity : BaseBindingActivity<ActivityMainBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    private val wordsViewModel by viewModels<WordsViewModel>()

    override fun setNavController(): Int {
        return R.id.nav_host_fragment
    }

    override fun initializeListeners() {
        initBottomNavigationListeners()

        binding.buttonGo.setOnClickListener { goToNextGoActivity() }
    }

    private fun initBottomNavigationListeners() {
        binding.navigationMenu.setOnItemReselectedListener {
            navigationFromBottomMenu(it)
        }

        binding.navigationMenu.setOnItemSelectedListener {
            wordsViewModel.clearData()
            navigationFromBottomMenu(it)
            true
        }
    }

    private fun navigationFromBottomMenu(item: MenuItem) {
        when (item.itemId) {
            R.id.menuItemWords -> goToNextWords()
            R.id.menuItemCategory -> goToNextCategory()
            R.id.menuItemProfile -> goToNextProfile()
            R.id.menuItemMenu -> goToNextMenu()
        }
    }

    private fun getNavOptions() = NavOptions.Builder()
        .setLaunchSingleTop(true)
        .setEnterAnim(R.anim.nav_enter_anim)
        .setExitAnim(R.anim.waite_anim)
        .setPopEnterAnim(R.anim.waite_anim)
        .setPopExitAnim(R.anim.nav_pop_exit_anim)
        .setPopUpTo(mNavController.graph.startDestinationId, false)
        .build()

    override fun onBackPressed() {
        super.onBackPressed()

        if (mNavController.currentDestination?.id == R.id.fragment_words) {
            binding.navigationMenu.selectedItemId = R.id.menuItemWords
        }
    }

    private fun goToNextWords() {
        mNavController.navigate(
            MainActivityDirections.actionGlobalToFragmentWords(),
            getNavOptions()
        )
    }

    private fun goToNextCategory() {
        mNavController.navigate(
            MainActivityDirections.actionGlobalToFragmentCategory(),
            getNavOptions()
        )
    }

    private fun goToNextProfile() {
        mNavController.navigate(
            MainActivityDirections.actionGlobalToFragmentProfile(),
            getNavOptions()
        )
    }

    private fun goToNextMenu() {
        mNavController.navigate(
            MainActivityDirections.actionGlobalToFragmentMenu(),
            getNavOptions()
        )
    }

    private fun goToNextGoActivity() {
        mNavController.navigate(MainActivityDirections.toActivityGo())
    }
}
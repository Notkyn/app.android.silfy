package ua.notky.silfy.ui.activity

import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ua.notky.base.ui.activity.BaseBindingActivity
import ua.notky.silfy.R
import ua.notky.silfy.databinding.ActivityMainBinding
import ua.notky.silfy.viewmodel.StateViewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@AndroidEntryPoint
class MainActivity : BaseBindingActivity<ActivityMainBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    private val stateViewModel by viewModels<StateViewModel>()

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
            stateViewModel.setDefaultSort()
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

    override fun onBackPressed() {
        super.onBackPressed()

        if (mNavController.currentDestination?.id == R.id.fragment_words) {
            binding.navigationMenu.selectedItemId = R.id.menuItemWords
        }
    }

    private fun goToNextWords() {
        mNavController.navigate(R.id.action_global_to_fragmentWords)
    }

    private fun goToNextCategory() {
        mNavController.navigate(R.id.action_global_to_fragmentCategory)
    }

    private fun goToNextProfile() {
        mNavController.navigate(R.id.action_global_to_fragmentProfile)
    }

    private fun goToNextMenu() {
        mNavController.navigate(R.id.action_global_to_fragmentMenu)
    }

    private fun goToNextGoActivity() {
        mNavController.navigate(R.id.to_activity_go)
    }
}
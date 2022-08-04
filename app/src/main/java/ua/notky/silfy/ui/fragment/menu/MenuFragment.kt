package ua.notky.silfy.ui.fragment.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import ua.notky.base.extension.openSafeScreen
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.databinding.FragmentMenuBinding
import ua.notky.silfy.viewmodel.StateViewModel
import ua.notky.silfy.viewmodel.menu.MenuViewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class MenuFragment : BaseBindingFragment<FragmentMenuBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMenuBinding
        get() = FragmentMenuBinding::inflate

    private val menuViewModel by viewModels<MenuViewModel>()
    private val stateViewModel by viewModels<StateViewModel>()

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(menuViewModel)
            .build()
    }

    override fun initializeViews() {
        binding.model = menuViewModel.model
        binding.state = stateViewModel.state
    }

    override fun initializeListeners() {
        binding.buttonTraining.handleClick { onNextTrainingSettings() }
        binding.buttonDictionary.handleClick { onNextDictionaryMenu() }
        binding.buttonProfile.handleClick { onNextProfileMenu() }
        binding.buttonHelp.handleClick { onNextHelpMenu() }
        binding.buttonAdmin.handleClick { onNextAdminMenu() }
    }

    override fun initializeViewModels() {
        stateViewModel.checkAdmin()
        menuViewModel.fetchData()
    }

    private fun onNextTrainingSettings() {
        openSafeScreen(MenuFragmentDirections.actionFragmentMenuToFragmentTrainingSettingsMenu())
    }

    private fun onNextDictionaryMenu() {
        openSafeScreen(MenuFragmentDirections.actionFragmentMenuToFragmentDictionaryMenu())
    }

    private fun onNextProfileMenu() {
        openSafeScreen(MenuFragmentDirections.actionFragmentMenuToFragmentProfileMenu())
    }

    private fun onNextHelpMenu() {
        openSafeScreen(MenuFragmentDirections.actionFragmentMenuToFragmentInfoMenu())
    }

    private fun onNextAdminMenu() {
        openSafeScreen(MenuFragmentDirections.actionFragmentMenuToFragmentAdminMenu())
    }
}
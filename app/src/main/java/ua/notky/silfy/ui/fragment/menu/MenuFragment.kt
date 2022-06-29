package ua.notky.silfy.ui.fragment.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import ua.notky.base.extension.openSafeScreen
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.databinding.FragmentMenuBinding
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

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(menuViewModel)
            .build()
    }

    override fun initializeViews() {
        binding.model = menuViewModel.model
    }

    override fun initializeListeners() {
        binding.buttonTraining.handleClick { onNextTrainingSettings() }
        binding.buttonDictionary.handleClick { onNextDictionaryMenu() }
        binding.buttonProfile.handleClick { onNextProfileMenu() }
    }

    override fun initializeViewModels() {
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
}
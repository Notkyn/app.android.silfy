package ua.notky.silfy.ui.fragment.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.util.toLog
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
        binding.buttonWorkout.handleClick { toLog("Click workout") }
        binding.buttonDictionary.handleClick { toLog("Click dictionary") }
        binding.buttonProfile.handleClick { toLog("Click profile") }
    }

    override fun initializeViewModels() {
        menuViewModel.fetchData()
    }
}
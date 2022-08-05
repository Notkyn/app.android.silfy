package ua.notky.silfy.ui.fragment.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ua.notky.base.extension.openSafePopBackstackScreen
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.silfy.databinding.FragmentMenuAdminBinding
import ua.notky.silfy.viewmodel.menu.MenuAdminViewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 05.08.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@AndroidEntryPoint
class AdminMenuFragment : BaseBindingFragment<FragmentMenuAdminBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMenuAdminBinding
        get() = FragmentMenuAdminBinding::inflate

    private val adminViewModel by viewModels<MenuAdminViewModel>()

    override fun initializeViews() {
        binding.state = adminViewModel.state
    }

    override fun initializeListeners() {
        binding.header.handleBackClick { openSafePopBackstackScreen() }

        binding.buttonLoadCategories.setOnClickListener { adminViewModel.fetchCategories() }
        binding.buttonLoadWords.setOnClickListener { adminViewModel.fetchWords() }
    }
}
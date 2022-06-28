package ua.notky.silfy.ui.fragment.menu.dictionary

import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.extension.openSafePopBackstackScreen
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.silfy.databinding.FragmentMenuDictionaryBinding

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 29.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class DictionaryMenuFragment : BaseBindingFragment<FragmentMenuDictionaryBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMenuDictionaryBinding
        get() = FragmentMenuDictionaryBinding::inflate

    override fun initializeListeners() {
        binding.header.handleBackClick { openSafePopBackstackScreen() }
    }
}
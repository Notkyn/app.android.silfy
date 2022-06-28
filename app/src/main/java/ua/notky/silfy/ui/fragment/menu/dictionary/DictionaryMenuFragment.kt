package ua.notky.silfy.ui.fragment.menu.dictionary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import ua.notky.base.extension.observe
import ua.notky.base.extension.openSafePopBackstackScreen
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.databinding.FragmentMenuDictionaryBinding
import ua.notky.silfy.models.model.DictionaryInfo
import ua.notky.silfy.viewmodel.menu.MenuDictionaryViewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 29.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class DictionaryMenuFragment : BaseBindingFragment<FragmentMenuDictionaryBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMenuDictionaryBinding
        get() = FragmentMenuDictionaryBinding::inflate

    private val dictionaryViewModel by viewModels<MenuDictionaryViewModel>()

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(dictionaryViewModel)
            .build()
    }

    override fun initializeViewModels() {
        dictionaryViewModel.fetchData()

        observe(dictionaryViewModel.dictionaryInfo, ::renderDictionaryInfo)
    }

    private fun renderDictionaryInfo(info: DictionaryInfo?) {
        binding.model = info
    }

    override fun initializeListeners() {
        binding.header.handleBackClick { openSafePopBackstackScreen() }

        binding.cardAll.handleClick {  }
        binding.cardFavourites.handleClick {  }
        binding.cardBlack.handleClick {  }
    }
}
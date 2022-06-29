package ua.notky.silfy.ui.fragment.menu.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import ua.notky.base.extension.observe
import ua.notky.base.extension.openSafePopBackstackScreen
import ua.notky.base.ui.dialog.exstensions.doOnConfirm
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.databinding.FragmentMenuDictionaryBinding
import ua.notky.silfy.models.enums.DictionaryCardType
import ua.notky.silfy.models.model.DictionaryInfo
import ua.notky.silfy.ui.dialog.menu.CleanDictionaryBottomsheet
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

        binding.cardAll.handleClick { showCleanDialog(DictionaryCardType.ALL) }
        binding.cardFavourites.handleClick { showCleanDialog(DictionaryCardType.FAVOURITE) }
        binding.cardBlack.handleClick { showCleanDialog(DictionaryCardType.BLACK) }
    }

    private fun showCleanDialog(type: DictionaryCardType) {
        val dialog = CleanDictionaryBottomsheet(type)

        dialog.doOnConfirm { onClean(type) }

        dialog.show(parentFragmentManager, dialog::class.java.simpleName)
    }

    private fun onClean(type: DictionaryCardType) {
        when(type) {
            DictionaryCardType.ALL -> dictionaryViewModel.onCleanAllProgress(requireContext())
            DictionaryCardType.FAVOURITE -> dictionaryViewModel.onCleanFavourites()
            DictionaryCardType.BLACK -> dictionaryViewModel.onCleanBlacks()
        }
    }
}
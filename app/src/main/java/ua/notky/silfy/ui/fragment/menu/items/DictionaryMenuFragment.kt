package ua.notky.silfy.ui.fragment.menu.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ua.notky.base.extension.observe
import ua.notky.base.extension.openSafePopBackstackScreen
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.databinding.FragmentMenuDictionaryBinding
import ua.notky.silfy.models.model.DictionaryInfo
import ua.notky.silfy.models.states.DictionaryUiState
import ua.notky.silfy.viewmodel.StateViewModel
import ua.notky.silfy.viewmodel.menu.MenuDictionaryViewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 29.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@AndroidEntryPoint
class DictionaryMenuFragment : BaseBindingFragment<FragmentMenuDictionaryBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMenuDictionaryBinding
        get() = FragmentMenuDictionaryBinding::inflate

    private val dictionaryViewModel by viewModels<MenuDictionaryViewModel>()
    private val stateViewModel by viewModels<StateViewModel>()

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(dictionaryViewModel)
            .build()
    }

    override fun initializeViews() {
        binding.state = stateViewModel.state
    }

    override fun initializeViewModels() {
        dictionaryViewModel.fetchData()

        observe(dictionaryViewModel.dictionaryInfo, ::renderDictionaryInfo)
        observe(dictionaryViewModel.uiState, ::renderUiState)
    }

    private fun renderDictionaryInfo(info: DictionaryInfo?) {
        binding.model = info
    }

    private fun renderUiState(state: DictionaryUiState?) {
        stateViewModel.setLoading(state == DictionaryUiState.Updating)
    }

    override fun initializeListeners() {
        binding.header.handleBackClick { openSafePopBackstackScreen() }

        binding.cardAll.handleClear { dictionaryViewModel.onCleanAllProgress(requireContext()) }
        binding.cardFavourites.handleClear { dictionaryViewModel.onCleanFavourites() }
        binding.cardBlack.handleClear { dictionaryViewModel.onCleanBlacks() }
    }
}
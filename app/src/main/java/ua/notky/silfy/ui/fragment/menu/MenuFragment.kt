package ua.notky.silfy.ui.fragment.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.databinding.FragmentMenuBinding

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class MenuFragment : BaseBindingFragment<FragmentMenuBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMenuBinding
        get() = FragmentMenuBinding::inflate

    override fun initialize() {}

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder().build()
    }
}
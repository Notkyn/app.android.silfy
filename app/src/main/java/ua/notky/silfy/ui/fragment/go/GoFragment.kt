package ua.notky.silfy.ui.fragment.go

import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.silfy.databinding.FragmentGoBinding

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 08.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class GoFragment : BaseBindingFragment<FragmentGoBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentGoBinding
        get() = FragmentGoBinding::inflate
}
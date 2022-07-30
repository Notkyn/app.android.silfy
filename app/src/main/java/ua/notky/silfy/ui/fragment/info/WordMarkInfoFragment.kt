package ua.notky.silfy.ui.fragment.info

import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.silfy.databinding.FragmentInfoWordMarkBinding

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 30.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class WordMarkInfoFragment : BaseBindingFragment<FragmentInfoWordMarkBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentInfoWordMarkBinding
        get() = FragmentInfoWordMarkBinding::inflate
}
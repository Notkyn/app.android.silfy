package ua.notky.silfy.ui.fragment.info

import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.silfy.databinding.FragmentInfoStateLevelBinding

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 30.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class StateLevelInfoFragment : BaseBindingFragment<FragmentInfoStateLevelBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentInfoStateLevelBinding
        get() = FragmentInfoStateLevelBinding::inflate
}
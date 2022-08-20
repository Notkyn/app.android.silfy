package ua.notky.silfy.ui.fragment.info

import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.silfy.databinding.FragmentInfoDifficultLevelBinding

class DifficultLevelInfoFragment : BaseBindingFragment<FragmentInfoDifficultLevelBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentInfoDifficultLevelBinding
        get() = FragmentInfoDifficultLevelBinding::inflate
}
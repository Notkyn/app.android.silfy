package ua.notky.silfy.ui.fragment.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.databinding.FragmentProfileBinding

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class ProfileFragment : BaseBindingFragment<FragmentProfileBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileBinding
        get() = FragmentProfileBinding::inflate

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder().build()
    }
}
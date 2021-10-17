package ua.notky.silfy.ui.fragment.words

import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.databinding.FragmentWordsBinding

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class WordsFragment : BaseBindingFragment<FragmentWordsBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentWordsBinding
        get() = FragmentWordsBinding::inflate

    override fun init() {}

    override fun buildViewModels(): ViewModelSet {
        return ViewModelSet.Builder().build()
    }
}
package ua.notky.silfy.ui.fragment.words

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayout
import ua.notky.base.listeners.BaseTabSelectListener
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.databinding.FragmentWordsBinding
import ua.notky.silfy.viewmodel.StateViewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class WordsFragment : BaseBindingFragment<FragmentWordsBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentWordsBinding
        get() = FragmentWordsBinding::inflate

    private val stateViewModel by activityViewModels<StateViewModel>()

    override fun init() {}

    override fun buildViewModels(): ViewModelSet {
        return ViewModelSet.Builder().build()
    }

    override fun initViews() {
        binding.state = stateViewModel.state
    }

    override fun initListeners() {
        initSortListeners()
        iniTabLayoutListener()
    }

    override fun initViewModels() {
        stateViewModel.setDefaultSort()
    }

    private fun initSortListeners() {
        binding.includeSort.imageSortEn.setOnClickListener {
            stateViewModel.setEnSort()
        }

        binding.includeSort.imageSortRu.setOnClickListener {
            stateViewModel.setRuSort()
        }

        binding.includeSort.imageSortType.setOnClickListener {
            stateViewModel.setTypeSort()
        }

        binding.includeSort.imageSortState.setOnClickListener {
            stateViewModel.setStateSort()
        }
    }

    private fun iniTabLayoutListener() {
        binding.includeHeader.tabLayout.addOnTabSelectedListener(object : BaseTabSelectListener(){
            override fun onTabSelected(tab: TabLayout.Tab?) {
                stateViewModel.setDefaultSort()
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                stateViewModel.setDefaultSort()
            }
        })
    }
}
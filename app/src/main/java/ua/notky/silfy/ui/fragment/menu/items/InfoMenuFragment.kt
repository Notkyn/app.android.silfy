package ua.notky.silfy.ui.fragment.menu.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import ua.notky.base.extension.openSafePopBackstackScreen
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.silfy.databinding.FragmentMenuInfoBinding
import ua.notky.silfy.ui.adapter.page.InfoPageAdapter
import ua.notky.silfy.viewmodel.StateViewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 30.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class InfoMenuFragment : BaseBindingFragment<FragmentMenuInfoBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMenuInfoBinding
        get() = FragmentMenuInfoBinding::inflate

    private val stateViewModel by viewModels<StateViewModel>()

    private val infoPageAdapter: InfoPageAdapter by lazy {
        return@lazy InfoPageAdapter(
            requireActivity()
        )
    }

    override fun initializeViews() {
        binding.state = stateViewModel.state
        initializePager()
        initTabLayout()
    }

    private fun initializePager() {
        binding.pager.adapter = InfoPageAdapter(requireActivity())
        binding.pager.currentItem = InfoPageAdapter.INFO_STATE_LEVEL_PAGE

        binding.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                stateViewModel.updateInfoMenuStates(position, infoPageAdapter.itemCount)
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
            }
        })
    }

    private fun initTabLayout() {
        for (index in 0 until infoPageAdapter.itemCount) {
            binding.tabLayout.addTab(binding.tabLayout.newTab())
        }

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, _ ->
            binding.pager.setCurrentItem(tab.position, true)
        }.attach()
    }

    override fun initializeListeners() {
        binding.header.handleBackClick { openSafePopBackstackScreen() }
        binding.buttonPrevious.setOnClickListener { onPreviousPage() }
        binding.buttonNext.setOnClickListener { onNextPage() }
    }

    private fun onPreviousPage() {
        binding.pager.currentItem = binding.pager.currentItem - 1
    }

    private fun onNextPage() {
        binding.pager.currentItem = binding.pager.currentItem + 1
    }
}
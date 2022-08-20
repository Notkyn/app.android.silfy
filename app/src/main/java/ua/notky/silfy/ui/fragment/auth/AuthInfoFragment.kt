package ua.notky.silfy.ui.fragment.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import ua.notky.base.extension.openSafeScreen
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.silfy.databinding.FragmentAuthInfoBinding
import ua.notky.silfy.ui.adapter.page.InfoPageAdapter
import ua.notky.silfy.ui.adapter.page.InfoPageAdapter.Companion.INFO_STATE_LEVEL_PAGE
import ua.notky.silfy.viewmodel.StateViewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 30.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class AuthInfoFragment : BaseBindingFragment<FragmentAuthInfoBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAuthInfoBinding
        get() = FragmentAuthInfoBinding::inflate

    private val stateViewModel by viewModels<StateViewModel>()

    private val infoPageAdapter: InfoPageAdapter by lazy {
        return@lazy InfoPageAdapter(
            requireActivity()
        )
    }

    override fun initialize(savedInstanceState: Bundle?) {
        super.initialize(savedInstanceState)
        addCallBackForHandleOnBackPressedEvent()
    }

    override fun initializeViews() {
        binding.state = stateViewModel.state
        initializePager()
        initTabLayout()
    }

    private fun initializePager() {
        binding.pager.adapter = InfoPageAdapter(requireActivity())
        binding.pager.currentItem = INFO_STATE_LEVEL_PAGE

        if (infoPageAdapter.itemCount == 0) {
            goToNextApplication()
        }

        binding.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                stateViewModel.updateInfoStates(position, infoPageAdapter.itemCount)
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
        binding.buttonPrevious.setOnClickListener { onPreviousPage() }
        binding.buttonNext.setOnClickListener { onNextPage() }
        binding.buttonOk.setOnClickListener { goToNextApplication() }
    }

    private fun addCallBackForHandleOnBackPressedEvent() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            when (binding.pager.currentItem) {
                INFO_STATE_LEVEL_PAGE -> {}
                else -> onPreviousPage()
            }
        }
    }

    private fun onPreviousPage() {
        binding.pager.currentItem = binding.pager.currentItem - 1
    }

    private fun onNextPage() {
        binding.pager.currentItem = binding.pager.currentItem + 1
    }

    private fun goToNextApplication() {
        openSafeScreen(AuthInfoFragmentDirections.toActivityMain())
        activity?.finishAffinity()
    }
}
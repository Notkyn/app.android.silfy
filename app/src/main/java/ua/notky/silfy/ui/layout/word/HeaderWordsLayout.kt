package ua.notky.silfy.ui.layout.word

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import ua.notky.base.listeners.BaseTabSelectListener
import ua.notky.base.ui.layout.liner.BaseBindingLinerLayout
import ua.notky.silfy.databinding.LayoutHeaderWordsBinding

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 11.05.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class HeaderWordsLayout(context: Context, attrs: AttributeSet? = null) :
    BaseBindingLinerLayout<LayoutHeaderWordsBinding>(context, attrs) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> LayoutHeaderWordsBinding
        get() = LayoutHeaderWordsBinding::inflate

    private var actionTabSelect: ((position: Int) -> Unit)? = null

    override fun initializeViews() {
        initializeTabLayout()
    }

    private fun initializeTabLayout() {
        binding.tabLayout.addOnTabSelectedListener(object : BaseTabSelectListener() {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                onTabSelected()
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                onTabSelected()
            }
        })
    }

    private fun onTabSelected() {
        actionTabSelect?.invoke(binding.tabLayout.selectedTabPosition)
    }

    fun handleTabSelected(action: (position: Int) -> Unit) {
        actionTabSelect = action
    }

    fun selectTab(index: Int) {
        binding.tabLayout.selectTab(binding.tabLayout.getTabAt(index))
    }
}
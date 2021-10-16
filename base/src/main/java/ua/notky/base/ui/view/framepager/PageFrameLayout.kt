package ua.notky.base.ui.view.framepager

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.transition.TransitionManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

class PageFrameLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var fragmentManager: FragmentManager? = null
    private var tabLayout: TabLayout? = null
    private var currentItemId: Int = INVALID_INDEX_ID
    private var rootLayout: ViewGroup? = null
    var adapter: FragmentStateAdapter? = null

    fun init(fragmentManager: FragmentManager) {
        this.fragmentManager = fragmentManager
    }

    fun init(fragmentManager: FragmentManager, startItemId: Int?) {
        init(fragmentManager)

        adapter?.let {
            if (checkItemId(startItemId)) {
                setCurrentItem(startItemId!!)
            } else {
                setCurrentItem(START_INDEX_ID)
            }
        }
    }

    fun setCurrentItem(itemId: Int) {
        adapter?.let {
            if (checkItemId(itemId)) {
                currentItemId = itemId
                startAnimation()
                transactionFragment(
                    it.createFragment(itemId)
                )
                selectTab(itemId)
            }
        }
    }

    fun setCurrentItemWithAddBackstack(itemId: Int) {
        adapter?.let {
            if (checkItemId(itemId)) {
                currentItemId = itemId
                startAnimation()
                transactionFragmentWithAddBackstack(
                    it.createFragment(itemId)
                )
                selectTab(itemId)
            }
        }
    }

    private fun transactionFragment(item: Fragment) {
        fragmentManager?.beginTransaction()?.replace(
            this.id,
            item
        )?.commit()
    }

    private fun transactionFragmentWithAddBackstack(item: Fragment) {
        fragmentManager?.beginTransaction()?.replace(
            this.id,
            item
        )?.addToBackStack(item::class.java.simpleName)?.commit()
    }

    private fun checkItemId(itemId: Int?): Boolean {
        val itemCount = adapter?.itemCount ?: 0

        return itemId != null
                && itemId >= 0
                && itemId < itemCount
    }

    fun getCurrentItemId(): Int {
        return currentItemId
    }

    fun setConstraintLayoutForAnimation(layout: ViewGroup) {
        rootLayout = layout
    }

    private fun startAnimation() {
        rootLayout?.let {
            TransitionManager.beginDelayedTransition(it)
        }
    }

    fun setTabLayout(layout: TabLayout?, enableTabs: Boolean?) {
        layout?.let{ tabLayout ->
            enableTabs?.let{ enables ->
                for(view in tabLayout.touchables) {
                    view.isEnabled = enables

                    view.setOnClickListener {
                        setCurrentItem(tabLayout.touchables.indexOf(it))
                    }
                }
            }

            this.tabLayout = tabLayout
        }
    }

    private fun selectTab(position: Int){
        tabLayout?.let {
            it.selectTab(it.getTabAt(position))
        }
    }

    companion object {
        const val START_INDEX_ID = 0
        const val INVALID_INDEX_ID = -1
    }
}
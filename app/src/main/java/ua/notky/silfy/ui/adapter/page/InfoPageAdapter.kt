package ua.notky.silfy.ui.adapter.page

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ua.notky.silfy.ui.fragment.info.StateLevelInfoFragment
import ua.notky.silfy.ui.fragment.info.WordMarkInfoFragment

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 30.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class InfoPageAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return INFO_PAGES
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            INFO_STATE_LEVEL_PAGE -> StateLevelInfoFragment()
            INFO_WORD_MARK_PAGE -> WordMarkInfoFragment()
            else -> throw Exception("Wrong pages count")
        }
    }

    companion object {
        const val INFO_STATE_LEVEL_PAGE = 0
        const val INFO_WORD_MARK_PAGE = 1

        const val INFO_PAGES = 2
    }
}
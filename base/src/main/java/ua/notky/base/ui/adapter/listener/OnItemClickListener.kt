package ua.notky.base.ui.adapter.listener

import android.view.View

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

interface OnItemClickListener<M> {
    fun onItemClick(item: M)
    fun onItemClick(view: View?)
    fun onItemClick(item: M, view: View?)
}
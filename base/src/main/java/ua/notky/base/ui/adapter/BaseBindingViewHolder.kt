package ua.notky.base.ui.adapter

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

class BaseBindingViewHolder<VDB : ViewDataBinding>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val binding: VDB? = DataBindingUtil.bind(itemView)
}
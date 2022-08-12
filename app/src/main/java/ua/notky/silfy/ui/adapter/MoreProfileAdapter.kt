package ua.notky.silfy.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.adapter.BaseBindingRecyclerListAdapter
import ua.notky.base.ui.adapter.holders.BaseBindingViewHolder
import ua.notky.silfy.databinding.ItemProfileMoreBinding
import ua.notky.silfy.models.model.Profile

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 12.08.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class MoreProfileAdapter : BaseBindingRecyclerListAdapter<Profile, ItemProfileMoreBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemProfileMoreBinding
        get() = ItemProfileMoreBinding::inflate

    override fun bindViewHolder(
        holder: BaseBindingViewHolder<ItemProfileMoreBinding>,
        model: Profile?
    ) {
        holder.binding?.model = model

        holder.binding?.root?.setOnClickListener {
            model?.let { mOnRootClickListener?.onRootClick(it) }
        }
    }
}
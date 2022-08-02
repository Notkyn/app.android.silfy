package ua.notky.silfy.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.adapter.BaseBindingRecyclerListAdapter
import ua.notky.base.ui.adapter.holders.BaseBindingViewHolder
import ua.notky.silfy.databinding.ItemMenuProfileBinding
import ua.notky.silfy.models.observable.ProfileMenuItemModel
import ua.notky.silfy.ui.adapter.diffutil.ProfileMenuDiffUtil

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 25.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class MenuProfileAdapter :
    BaseBindingRecyclerListAdapter<ProfileMenuItemModel, ItemMenuProfileBinding>(
        ProfileMenuDiffUtil()
    ) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemMenuProfileBinding
        get() = ItemMenuProfileBinding::inflate

    override fun bindViewHolder(
        holder: BaseBindingViewHolder<ItemMenuProfileBinding>,
        model: ProfileMenuItemModel?
    ) {
        holder.binding?.model = model

        holder.binding?.buttonDelete?.setOnClickListener {
            model?.let { mOnActionDeleteListener?.onDelete(it) }
        }
    }
}
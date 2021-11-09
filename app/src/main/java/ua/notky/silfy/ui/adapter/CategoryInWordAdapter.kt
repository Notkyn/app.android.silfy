package ua.notky.silfy.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.adapter.BaseBindingRecyclerListAdapter
import ua.notky.base.ui.adapter.BaseBindingViewHolder
import ua.notky.silfy.databinding.ItemCategoryInWordBinding
import ua.notky.silfy.models.model.Category

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 09.11.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class CategoryInWordAdapter : BaseBindingRecyclerListAdapter<Category, ItemCategoryInWordBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemCategoryInWordBinding
        get() = ItemCategoryInWordBinding::inflate

    override fun bindViewHolder(
        holder: BaseBindingViewHolder<ItemCategoryInWordBinding>,
        model: Category
    ) {
        holder.binding?.model = model

        holder.binding?.root?.setOnClickListener {
            mOnItemClickListener?.onItemClick(model)
        }
    }
}
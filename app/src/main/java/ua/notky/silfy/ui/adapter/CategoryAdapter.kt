package ua.notky.silfy.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.adapter.BaseBindingRecyclerListAdapter
import ua.notky.base.ui.adapter.holders.BaseBindingViewHolder
import ua.notky.silfy.databinding.ItemCategoryBinding
import ua.notky.silfy.models.model.Category

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 05.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class CategoryAdapter : BaseBindingRecyclerListAdapter<Category, ItemCategoryBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemCategoryBinding
        get() = ItemCategoryBinding::inflate

    override fun bindViewHolder(
        holder: BaseBindingViewHolder<ItemCategoryBinding>,
        model: Category?
    ) {
        holder.binding?.model = model
    }
}
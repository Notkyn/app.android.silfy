package ua.notky.silfy.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.adapter.BaseBindingRecyclerListAdapter
import ua.notky.base.ui.adapter.holders.BaseBindingViewHolder
import ua.notky.silfy.databinding.ItemSelectCategoryBinding
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.ui.adapter.diffutil.CategoryDiffUtil

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class SelectCategoryAdapter :
    BaseBindingRecyclerListAdapter<Category, ItemSelectCategoryBinding>(CategoryDiffUtil()) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemSelectCategoryBinding
        get() = ItemSelectCategoryBinding::inflate

    override fun bindViewHolder(
        holder: BaseBindingViewHolder<ItemSelectCategoryBinding>,
        model: Category?
    ) {
        holder.binding?.model = model

        holder.binding?.root?.setOnClickListener {
            model?.let { item -> mOnRootClickListener?.onRootClick(item) }
        }
    }
}
package ua.notky.silfy.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.adapter.BaseBindingRecyclerListAdapter
import ua.notky.base.ui.adapter.BaseBindingViewHolder
import ua.notky.silfy.databinding.ItemWordBinding
import ua.notky.silfy.models.model.Word

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 22.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class WordAdapter : BaseBindingRecyclerListAdapter<Word, ItemWordBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemWordBinding
        get() = ItemWordBinding::inflate

    override fun bindViewHolder(holder: BaseBindingViewHolder<ItemWordBinding>, model: Word) {
        holder.binding?.model = model
    }
}
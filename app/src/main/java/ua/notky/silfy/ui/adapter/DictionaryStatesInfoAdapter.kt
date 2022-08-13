package ua.notky.silfy.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.adapter.BaseBindingRecyclerListAdapter
import ua.notky.base.ui.adapter.holders.BaseBindingViewHolder
import ua.notky.silfy.databinding.ItemDictionaryStatesInfoBinding
import ua.notky.silfy.models.model.DictionaryByStateInfo

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 13.08.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class DictionaryStatesInfoAdapter :
    BaseBindingRecyclerListAdapter<DictionaryByStateInfo, ItemDictionaryStatesInfoBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemDictionaryStatesInfoBinding
        get() = ItemDictionaryStatesInfoBinding::inflate

    override fun bindViewHolder(
        holder: BaseBindingViewHolder<ItemDictionaryStatesInfoBinding>,
        model: DictionaryByStateInfo?
    ) {
        holder.binding?.model = model
    }
}
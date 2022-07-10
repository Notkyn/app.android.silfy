package ua.notky.silfy.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.adapter.BaseBindingRecyclerListAdapter
import ua.notky.base.ui.adapter.holders.BaseBindingViewHolder
import ua.notky.silfy.databinding.ItemAnswerWordSymbolBinding
import ua.notky.silfy.models.observable.SymbolModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 10.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class AnswerWordSymbolAdapter :
    BaseBindingRecyclerListAdapter<SymbolModel, ItemAnswerWordSymbolBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemAnswerWordSymbolBinding
        get() = ItemAnswerWordSymbolBinding::inflate

    override fun bindViewHolder(
        holder: BaseBindingViewHolder<ItemAnswerWordSymbolBinding>,
        model: SymbolModel?
    ) {
        holder.binding?.model = model

        holder.binding?.buttonSymbol?.setOnClickListener {
            model?.let { mOnItemClickListener?.onItemClick(it) }
        }
    }
}
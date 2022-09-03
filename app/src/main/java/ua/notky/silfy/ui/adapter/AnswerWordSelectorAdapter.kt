package ua.notky.silfy.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.adapter.BaseBindingRecyclerListAdapter
import ua.notky.base.ui.adapter.holders.BaseBindingViewHolder
import ua.notky.silfy.databinding.ItemAnswerWordSelectBinding
import ua.notky.silfy.models.observable.answer.WordAnswerSelectModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 10.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class AnswerWordSelectorAdapter : BaseBindingRecyclerListAdapter<WordAnswerSelectModel, ItemAnswerWordSelectBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemAnswerWordSelectBinding
        get() = ItemAnswerWordSelectBinding::inflate

    override fun bindViewHolder(
        holder: BaseBindingViewHolder<ItemAnswerWordSelectBinding>,
        model: WordAnswerSelectModel?
    ) {
        model?.refresh()
        holder.binding?.model = model

        holder.binding?.button?.setOnClickListener { _ ->
            model?.let { mOnItemClickListener?.onItemClick(it) }
        }
    }
}
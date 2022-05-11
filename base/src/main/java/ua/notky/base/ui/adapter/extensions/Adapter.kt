package ua.notky.base.ui.adapter.extensions

import androidx.recyclerview.widget.RecyclerView
import ua.notky.base.ui.adapter.BaseRecyclerListAdapter

/**
 * @project Telesim
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 25.01.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@Suppress("UNCHECKED_CAST")
private fun <M> RecyclerView.castToBaseRecyclerAdapter(): BaseRecyclerListAdapter<M, RecyclerView.ViewHolder>? {
    return try {
        if (this.adapter is BaseRecyclerListAdapter<*, *>) {
            (this.adapter as BaseRecyclerListAdapter<M, RecyclerView.ViewHolder>)
        } else {
            throw IllegalStateException("Adapter ${this::class.java.simpleName} is not a BaseRecyclerListAdapter")
        }
    } catch (ex: Exception) {
        throw IllegalStateException("Don`t cast adapter ${this::class.java.simpleName} to BaseRecyclerListAdapter")
    }
}

fun <M> RecyclerView.addAllData(data: Collection<M>) {
    this.castToBaseRecyclerAdapter<M>()?.addAll(data)
}

fun <M> RecyclerView.updateAllData(data: Collection<M>) {
    this.castToBaseRecyclerAdapter<M>()?.updateAll(data)
}
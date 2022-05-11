package ua.notky.base.ui.adapter

import ua.notky.base.ui.adapter.listener.*

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

interface RecyclerCollectionAdapter<M> {
    /* diffutils */
    fun compareByDiffUtil(oldData: Collection<M>?, newData: Collection<M>?)
    fun compareByDiffUtil(oldData: Collection<M>?)
//    fun setDiffUtilCallback(diffUtilCallback: BaseDiffUtilCallback<M>)
//    fun getDiffUtilCallback(): BaseDiffUtilCallback<M>?

    /* listeners */
    fun setOnRootClickListener(onRootClickListener: OnRootClickListener<M>?)
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener<M>?)
    fun setOnItemLongClickListener(onItemLongClickListener: OnItemLongClickListener<M>?)
    fun setOnActionEditListener(onActionListener: OnRecyclerActionEditListener<M>?)
    fun setOnActionDeleteListener(onActionListener: OnRecyclerActionDeleteListener<M>?)
//    fun setOnActionListener(onActionListener: OnRecyclerActionListener<M>?)

    /* change data */
    fun addItem(item: M?)
    fun addItem(position: Int, item: M?)
    fun addAll(data: Collection<M>?)
    fun clearAndAddAll(data: Collection<M>?)
    fun updateItem(item: M?)
    fun updateAll(data: Collection<M>?)  // need?
    fun removeItem(item: M?)
    fun removeItem(position: Int)
    fun clear()
    fun restoreItem(item: M?, position: Int) // need?
    fun getItem(position: Int): M?
    fun getItemPosition(item: M?): Int
    fun getAll(): Collection<M>?
    fun isEmpty(): Boolean
}
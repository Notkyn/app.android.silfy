package ua.notky.base.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import java.util.*

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

abstract class BaseDiffUtilCallback<D> : DiffUtil.Callback {
    private val mOldList: MutableList<D>
    private val mNewList: MutableList<D>

    constructor() {
        mOldList = ArrayList()
        mNewList = ArrayList()
    }

    constructor(oldList: MutableList<D>, newList: MutableList<D>) {
        mOldList = oldList
        mNewList = newList
    }

    fun updateLists(oldList: Collection<D>?, newList: Collection<D>?) {
        setNewList(newList)
        setOldList(oldList)
    }

    fun setNewList(newList: Collection<D>?) {
        mNewList.clear()
        mNewList.addAll(newList!!)
    }

    fun setOldList(oldList: Collection<D>?) {
        mOldList.clear()
        mOldList.addAll(oldList!!)
    }

    override fun getOldListSize(): Int {
        return mOldList.size
    }

    override fun getNewListSize(): Int {
        return mNewList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = mOldList[oldItemPosition]
        val newItem = mNewList[newItemPosition]
        return areItemsTheSame(oldItem, newItem)
    }

    abstract fun areItemsTheSame(oldItem: D, newItem: D): Boolean
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = mOldList[oldItemPosition]
        val newItem = mNewList[newItemPosition]
        return areContentsTheSame(oldItem, newItem)
    }

    abstract fun areContentsTheSame(oldItem: D, newItem: D): Boolean
}

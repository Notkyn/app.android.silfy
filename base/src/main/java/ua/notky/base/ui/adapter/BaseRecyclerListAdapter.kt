package ua.notky.base.ui.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ua.notky.base.ui.adapter.diffutils.BaseDiffUtilCallback
import ua.notky.base.ui.adapter.listener.*

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

abstract class BaseRecyclerListAdapter<M, VH : RecyclerView.ViewHolder>() :
    RecyclerView.Adapter<VH>(), RecyclerCollectionAdapter<M> {

    protected var mList: MutableList<M> = mutableListOf()
    private var mDiffUtilCallback: BaseDiffUtilCallback<M>? = null
    protected var mOnRootClickListener: OnRootClickListener<M>? = null
    protected var mOnItemClickListener: OnItemClickListener<M>? = null
    protected var mOnItemLongClickListener: OnItemLongClickListener<M>? = null
    protected var mOnActionDeleteListener: OnRecyclerActionDeleteListener<M>? = null
    protected var mOnActionEditListener: OnRecyclerActionEditListener<M>? = null

    init {
        mList = mutableListOf()
    }

    constructor(list: List<M>) : this() {
        mList.addAll(list)
    }

    constructor(diffUtilCallback: BaseDiffUtilCallback<M>) : this() {
        mDiffUtilCallback = diffUtilCallback
    }

    constructor(list: List<M>, diffUtilCallback: BaseDiffUtilCallback<M>) : this(list) {
        mDiffUtilCallback = diffUtilCallback
    }

    override fun setOnRootClickListener(onRootClickListener: OnRootClickListener<M>?) {
        mOnRootClickListener = onRootClickListener
    }

    override fun setOnItemClickListener(onItemClickListener: OnItemClickListener<M>?) {
        mOnItemClickListener = onItemClickListener
    }

    override fun setOnItemLongClickListener(onItemLongClickListener: OnItemLongClickListener<M>?) {
        mOnItemLongClickListener = onItemLongClickListener
    }

    override fun setOnActionEditListener(onActionListener: OnRecyclerActionEditListener<M>?) {
        mOnActionEditListener = onActionListener
    }

    override fun setOnActionDeleteListener(onActionListener: OnRecyclerActionDeleteListener<M>?) {
        mOnActionDeleteListener = onActionListener
    }

    // Diff Utils Start
    override fun compareByDiffUtil(oldData: Collection<M>?) {
        compareByDiffUtil(oldData, mList)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun compareByDiffUtil(oldData: Collection<M>?, newData: Collection<M>?) {
        compareByDiffUtilWithNotify(oldData, newData) {
            notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun doDiffUtilWithNotify(type: Notify, block: () -> Int) {
        val oldData = ArrayList<M>(mList)

        val position = block.invoke()

        compareByDiffUtilWithNotify(oldData, mList) {
            when (type) {
                Notify.ALL -> notifyDataSetChanged()
                Notify.INSERTED -> notifyItemInserted(position)
                Notify.CHANGED -> notifyItemChanged(position)
                Notify.REMOVED -> notifyItemRemoved(position)
                Notify.RANGE -> notifyItemRangeChanged(oldData.size, mList.size - oldData.size)
            }
        }
    }

    private enum class Notify {
        ALL, INSERTED, CHANGED, REMOVED, RANGE
    }

    private fun compareByDiffUtilWithNotify(
        oldData: Collection<M>?,
        newData: Collection<M>?,
        actionWithoutDiffUtil: () -> Unit
    ) {
        val diffUtilResult = mDiffUtilCallback?.let {
            it.updateLists(oldData, newData)
            DiffUtil.calculateDiff(it)
        }

        if (diffUtilResult != null) {
            diffUtilResult.dispatchUpdatesTo(this)
        } else {
            actionWithoutDiffUtil.invoke()
        }
    }
    // Diff Utils End

    override fun addItem(item: M?) {
        item?.let {
            doDiffUtilWithNotify(Notify.INSERTED) {
                mList.add(it)
                mList.size
            }
        }
    }

    override fun addItem(position: Int, item: M?) {
        item?.let {
            if (position in 0 until itemCount) {
                doDiffUtilWithNotify(Notify.INSERTED) {
                    mList.add(position, it)
                    position
                }
            }
        }
    }

    override fun addAll(data: Collection<M>?) {
        if (!data.isNullOrEmpty()) {
            doDiffUtilWithNotify(Notify.RANGE) {
                mList.addAll(data)
                mList.size
            }
        }
    }

    override fun updateItem(item: M?) {
        item?.let {
            doDiffUtilWithNotify(Notify.CHANGED) {
                val position = getItemPosition(item)

                if (position in 0 until itemCount) {
                    mList.removeAt(position)
                    mList.add(position, it)
                }

                position
            }
        }
    }

    override fun updateAll(data: Collection<M>?) {
        clearAndAddAll(data)
    }

    override fun removeItem(item: M?) {
        item?.let {
            doDiffUtilWithNotify(Notify.ALL) {
                mList.remove(it)
                mList.size
            }
        }
    }

    override fun removeItem(position: Int) {
        getItem(position)?.let {
            doDiffUtilWithNotify(Notify.REMOVED) {
                mList.remove(it)
                position
            }
        }
    }

    override fun clearAndAddAll(data: Collection<M>?) {
        doDiffUtilWithNotify(Notify.ALL) {
            mList.clear()
            data?.let { mList.addAll(it) }
            mList.size
        }
    }

    override fun clear() {
        doDiffUtilWithNotify(Notify.ALL) {
            mList.clear()
            mList.size
        }
    }

    override fun isEmpty(): Boolean {
        return mList.isEmpty()
    }

    override fun restoreItem(item: M?, position: Int) {
        item?.let {
            doDiffUtilWithNotify(Notify.INSERTED) {
                mList.add(position, it)
                position
            }
        }
    }

    override fun getItem(position: Int): M? {
        return if (position in 0 until itemCount) {
            mList[position]
        } else {
            null
        }
    }

    override fun getItemPosition(item: M?): Int {
        return if (mList.contains(item)) {
            mList.indexOf(item)
        } else {
            -1
        }
    }

    override fun getAll(): Collection<M>? {
        return mList
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}

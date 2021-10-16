package ua.notky.base.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ua.notky.base.ui.adapter.listener.OnItemClickListener
import ua.notky.base.ui.adapter.listener.OnItemLongClickListener
import ua.notky.base.ui.adapter.listener.OnRecyclerActionDeleteListener
import ua.notky.base.ui.adapter.listener.OnRecyclerActionEditListener
import java.util.*

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

abstract class BaseRecyclerListAdapter<M, VH : RecyclerView.ViewHolder>() : RecyclerView.Adapter<VH>(),
    RecyclerCollectionAdapter<M> {

    protected var mList: MutableList<M> = mutableListOf()
    protected var mDiffUtilCallback: BaseDiffUtilCallback<M>? = null
    protected var mOnItemClickListener: OnItemClickListener<M>? = null
    protected var mOnItemLongClickListener: OnItemLongClickListener<M>? = null
    protected var mOnActionDeleteListener: OnRecyclerActionDeleteListener<M>? = null
    protected var mOnActionEditListener: OnRecyclerActionEditListener<M>? = null

    init {
        mList = mutableListOf()
    }

    constructor(list: List<M>): this() {
        mList.addAll(list)
    }

    constructor(diffUtilCallback: BaseDiffUtilCallback<M>): this() {
        mDiffUtilCallback = diffUtilCallback
    }

    constructor(list: List<M>, diffUtilCallback: BaseDiffUtilCallback<M>) : this(list) {
        mDiffUtilCallback = diffUtilCallback
    }

    override fun compareByDiffUtil(oldData: Collection<M>, newData: Collection<M>) {
        mDiffUtilCallback?.let {
            val oldCollection = Collections.unmodifiableCollection(oldData)

            it.updateLists(oldCollection, newData)
            val result = DiffUtil.calculateDiff(it)

            oldCollection.clear()
            oldCollection.addAll(newData)

            result.dispatchUpdatesTo(this)
        }
    }

    override fun compareByDiffUtil(oldData: Collection<M>) {
        compareByDiffUtil(oldData, Collections.unmodifiableCollection(mList))
    }

    override fun setDiffUtilCallback(diffUtilCallback: BaseDiffUtilCallback<M>) {
        mDiffUtilCallback = diffUtilCallback
    }

    override fun getDiffUtilCallback(): BaseDiffUtilCallback<M>? {
        return mDiffUtilCallback
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

    override fun addItem(item: M) {
        val oldData: List<M> = ArrayList(mList)

        mDiffUtilCallback?.let { diffUtil ->

            for (i in mList.indices) {
                if (diffUtil.areItemsTheSame(mList[i], item)) {
                    mList.removeAt(i)
                    mList.add(i, item)
                    break
                }
            }
            compareByDiffUtil(oldData)
        } ?: run {
            addInternal(item)
            notifyItemInserted(mList.size)
        }
    }

    override fun addItem(position: Int, item: M) {
        val oldData: List<M> = ArrayList(mList)

        addInternal(position, item)

        mDiffUtilCallback?.let{
            compareByDiffUtil(oldData)
        } ?: run {
            notifyItemInserted(position)
        }
    }

    override fun addAll(data: Collection<M>?) {
        if(!data.isNullOrEmpty()) {
            val oldData: List<M> = ArrayList(mList)

            for (item in data) {
                addInternal(item)
            }

            mDiffUtilCallback?.let {
                compareByDiffUtil(oldData)
            } ?: run {
                val addedSize = data.size
                val oldSize = mList.size - addedSize
                notifyItemRangeInserted(oldSize, addedSize)
            }
        }
    }

    override fun updateItem(item: M) {
        val oldData: List<M> = ArrayList(mList)

        mDiffUtilCallback?.let { diffUtil ->
            for (i in mList.indices) {
                if (diffUtil.areItemsTheSame(mList[i], item)) {
                    mList.removeAt(i)
                    mList.add(i, item)
                }
            }
            compareByDiffUtil(oldData)
        } ?: run {
            val position: Int = mList.indexOf(item)
            if (position >= 0) {
                mList.removeAt(position)
                mList.add(position, item)
                notifyItemChanged(position)
            }
        }
    }

    override fun updateAll(data: Collection<M>?) {
        data?.let {
            mDiffUtilCallback?.let {
                compareByDiffUtil(mList, data)
            } ?: run {
                clearAndAddAll(data)
            }
        }
    }

    override fun removeItem(item: M) {
        removeItem(mList.indexOf(item))
    }

    override fun removeItem(position: Int) {
        val oldData: List<M> = ArrayList(mList)

        if (position >= 0) {
            mList.removeAt(position)
            mDiffUtilCallback?.let {
                compareByDiffUtil(oldData)
            } ?: run {
                notifyItemRemoved(position)
            }
        }

        if (isEmpty()) {
            clear()
        }
    }

    override fun clearAndAddAll(data: Collection<M>?) {
        data?.let {
            mList.clear()
            for (item in data) {
                addInternal(item)
            }
            notifyDataSetChanged()
        }
    }

    override fun clear() {
        val oldData: List<M> = ArrayList(mList)

        mList.clear()
        mDiffUtilCallback?.let {
            compareByDiffUtil(oldData)
        } ?: run {
            notifyDataSetChanged()
        }
    }

    override fun isEmpty(): Boolean {
        return mList.isEmpty()
    }

    override fun restoreItem(item: M, position: Int) {
        val oldData: List<M> = ArrayList(mList)

        mList.add(position, item)
        mDiffUtilCallback?.let {
            compareByDiffUtil(oldData)
        } ?: run {
            notifyItemInserted(position)
        }
    }

    override fun getItem(position: Int): M {
        return mList[position]
    }

    override fun getAll(): Collection<M>? {
        return mList
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    private fun addInternal(item: M) {
        mList.add(item)
    }

    private fun addInternal(position: Int, item: M) {
        mList.add(position, item)
    }
}

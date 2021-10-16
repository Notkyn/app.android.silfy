package ua.notky.base.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import java.lang.ref.WeakReference

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

abstract class BaseBindingRecyclerListAdapter<M, VDB : ViewDataBinding> : BaseRecyclerListAdapter<M, BaseBindingViewHolder<VDB>>() {

    protected abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VDB

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingViewHolder<VDB> {
        val inflater = WeakReference(LayoutInflater.from(parent.context)).get()
        val binding = bindingInflater.invoke(requireNotNull(inflater), parent, false)

        return BaseBindingViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: BaseBindingViewHolder<VDB>, position: Int) {
        bindViewHolder(holder, mList[position])
    }

    abstract fun bindViewHolder(holder: BaseBindingViewHolder<VDB>, model: M)
}
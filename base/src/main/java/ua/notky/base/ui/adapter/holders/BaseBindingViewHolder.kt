package ua.notky.base.ui.adapter.holders

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import ua.notky.base.ui.adapter.animation.holder.ViewHolderAnimator
import ua.notky.base.ui.adapter.animation.holder.doWithStartAnim

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

class BaseBindingViewHolder<VDB : ViewDataBinding>(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    val binding: VDB? = DataBindingUtil.bind(itemView)
    private var viewHolderAnimator: ViewHolderAnimator? = null

    fun setAnimator(animator: ViewHolderAnimator?) {
        viewHolderAnimator = animator
    }

    fun bindWithAnimation(action: () -> Unit) {
        if (viewHolderAnimator != null) {
            viewHolderAnimator?.doWithStartAnim(this.itemView) { action.invoke() }
        } else {
            action.invoke()
        }
    }

    fun stopAnimation() {
        viewHolderAnimator?.cancelAnimation(this.itemView)
    }
}
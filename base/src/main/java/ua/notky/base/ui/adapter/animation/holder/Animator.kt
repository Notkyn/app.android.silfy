package ua.notky.base.ui.adapter.animation.holder

import android.animation.Animator
import android.view.View

/**
 * @project Telesim
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 22.01.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

inline fun ViewHolderAnimator.doWithStartAnim(
    view: View,
    crossinline action: () -> Unit
) {
    this.setListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {
            action.invoke()
        }

        override fun onAnimationEnd(animation: Animator) {}
        override fun onAnimationCancel(animation: Animator) {}
        override fun onAnimationRepeat(animation: Animator) {}

    })

    this.runAnimation(view)
}
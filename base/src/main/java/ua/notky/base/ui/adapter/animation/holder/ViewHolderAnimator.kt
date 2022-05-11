package ua.notky.base.ui.adapter.animation.holder

import android.animation.Animator
import android.view.View

/**
 * @project Telesim
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 22.01.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
interface ViewHolderAnimator {
    fun runAnimation(view: View)
    fun cancelAnimation(view: View)
    fun setListener(listener: Animator.AnimatorListener)
}
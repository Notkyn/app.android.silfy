package ua.notky.base.ui.adapter.animation.holder

import android.animation.Animator
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.AccelerateInterpolator
import android.view.animation.BaseInterpolator

/**
 * @project Telesim
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 22.01.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
abstract class BaseViewHolderAnimator(
    protected var interpolator: BaseInterpolator = AccelerateInterpolator(),
    protected var duration: Long
) : ViewHolderAnimator {
    private var listener: Animator.AnimatorListener = object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator?) { /* empty */ }
        override fun onAnimationEnd(animation: Animator?) { /* empty */ }
        override fun onAnimationCancel(animation: Animator?) { /* empty */ }
        override fun onAnimationRepeat(animation: Animator?) { /* empty */ }
    }

    override fun setListener(listener: Animator.AnimatorListener) {
        this.listener = listener
    }

    private fun configureAnimator(view: View): ViewPropertyAnimator {
        return view.animate()
            .setInterpolator(interpolator)
            .setDuration(duration)
            .setListener(listener)
    }

    override fun runAnimation(view: View) {
        prepareViewBeforeAnim(view)
        configurePropertyForAnim(configureAnimator(view)).start()
    }

    override fun cancelAnimation(view: View) {
        view.animate().cancel()
        repairViewAfterCancelAnim(view)
    }

    abstract fun configurePropertyForAnim(animator: ViewPropertyAnimator): ViewPropertyAnimator
    abstract fun prepareViewBeforeAnim(view: View)
    abstract fun repairViewAfterCancelAnim(view: View)
}
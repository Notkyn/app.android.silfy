package ua.notky.base.ui.adapter.animation.holder.simple

import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.BaseInterpolator
import ua.notky.base.ui.adapter.animation.holder.BaseViewHolderAnimator

/**
 * @project Telesim
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 22.01.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class AlphaViewHolderAnimator : BaseViewHolderAnimator {
    private var startValue: Float = 0f
    private var endValue: Float = 1f

    constructor(
        interpolator: BaseInterpolator,
        duration: Long,
        startValue: Float,
        endValue: Float
    ) : super(interpolator, duration) {
        this.startValue = startValue
        this.endValue = endValue
    }

    constructor(
        duration: Long,
        startValue: Float,
        endValue: Float
    ) : super(duration = duration) {
        this.startValue = startValue
        this.endValue = endValue
    }

    override fun configurePropertyForAnim(animator: ViewPropertyAnimator): ViewPropertyAnimator {
        return animator.alpha(endValue)
    }

    override fun prepareViewBeforeAnim(view: View) {
        view.alpha = startValue
    }

    override fun repairViewAfterCancelAnim(view: View) {
        view.alpha = endValue
    }
}
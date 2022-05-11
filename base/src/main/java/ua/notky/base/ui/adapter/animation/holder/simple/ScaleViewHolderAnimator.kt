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
class ScaleViewHolderAnimator : BaseViewHolderAnimator {
    private var startValueX: Float = 0f
    private var endValueX: Float = 1f
    private var startValueY: Float = 0f
    private var endValueY: Float = 1f

    constructor(
        interpolator: BaseInterpolator,
        duration: Long,
        startValueX: Float,
        endValueX: Float,
        startValueY: Float,
        endValueY: Float
    ) : super(interpolator, duration) {
        this.startValueX = startValueX
        this.endValueX = endValueX
        this.startValueY = startValueY
        this.endValueY = endValueY
    }

    constructor(
        duration: Long,
        startValueX: Float,
        endValueX: Float,
        startValueY: Float,
        endValueY: Float
    ) : super(duration = duration) {
        this.startValueX = startValueX
        this.endValueX = endValueX
        this.startValueY = startValueY
        this.endValueY = endValueY
    }

    override fun configurePropertyForAnim(animator: ViewPropertyAnimator): ViewPropertyAnimator {
        return animator.scaleX(endValueX).scaleY(endValueY)
    }

    override fun prepareViewBeforeAnim(view: View) {
        view.scaleX = startValueX
        view.scaleY = startValueY
    }

    override fun repairViewAfterCancelAnim(view: View) {
        view.scaleX = endValueX
        view.scaleY = endValueY
    }
}
package ua.notky.silfy.ui.view.ring

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import ua.notky.silfy.R

/**
 * 4j Accuracy ring on the ink results card: white 12% track, aqua arc from the top clockwise
 * for [percent] of the circle (as the design's conic gradient). Text goes on top of it in the layout.
 */
class AccuracyRingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    /** 0..100 */
    var percent: Int = 0
        set(value) {
            field = value.coerceIn(0, MAX_PERCENT)
            invalidate()
        }

    private val ringWidth = resources.displayMetrics.density * RING_WIDTH_DP

    private val trackPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = ringWidth
        color = TRACK_COLOR
    }

    private val arcPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = ringWidth
        strokeCap = Paint.Cap.BUTT
        color = ContextCompat.getColor(context, R.color.aqua)
    }

    private val bounds = RectF()

    init {
        importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_NO
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val inset = ringWidth / 2
        bounds.set(inset, inset, width - inset, height - inset)

        canvas.drawOval(bounds, trackPaint)
        if (percent > 0) {
            canvas.drawArc(bounds, START_ANGLE, FULL_CIRCLE * percent / MAX_PERCENT, false, arcPaint)
        }
    }

    private companion object {
        /** (164 − 132) / 2 */
        const val RING_WIDTH_DP = 16f
        const val START_ANGLE = -90f
        const val FULL_CIRCLE = 360f
        const val MAX_PERCENT = 100

        /** White 12% */
        const val TRACK_COLOR = 0x1FFFFFFF
    }
}

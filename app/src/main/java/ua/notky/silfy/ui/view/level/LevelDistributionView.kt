package ua.notky.silfy.ui.view.level

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import ua.notky.silfy.R
import ua.notky.silfy.models.states.WordState

/**
 * 5a Progress: words by knowledge level as one rounded bar (as the design's flex row with `gap: 2px`).
 * Each level with words gets a segment proportional to its count; without words the bar is empty.
 * Height comes from the layout (12dp).
 */
class LevelDistributionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var segments: List<Pair<Int, Int>> = emptyList()

    private val gap = resources.displayMetrics.density * GAP_DP
    private val emptyColor = ContextCompat.getColor(context, R.color.level_empty)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val clip = Path()
    private val rect = RectF()

    init {
        importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_NO
    }

    /** @param levels level → number of words, in drawing order (Excellent first) */
    fun setLevels(levels: List<Pair<WordState, Int>>) {
        segments = levels
            .filter { (_, count) -> count > 0 }
            .map { (state, count) -> ContextCompat.getColor(context, state.levelColor) to count }
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        clip.reset()
        rect.set(0f, 0f, w.toFloat(), h.toFloat())
        clip.addRoundRect(rect, h / 2f, h / 2f, Path.Direction.CW)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val total = segments.sumOf { it.second }
        val height = height.toFloat()

        canvas.save()
        canvas.clipPath(clip)

        if (total == 0) {
            paint.color = emptyColor
            canvas.drawRect(0f, 0f, width.toFloat(), height, paint)
        } else {
            val available = width - gap * (segments.size - 1)
            var left = 0f
            segments.forEachIndexed { index, (color, count) ->
                val right = if (index == segments.lastIndex) width.toFloat() else left + available * count / total
                paint.color = color
                canvas.drawRect(left, 0f, right, height, paint)
                left = right + gap
            }
        }

        canvas.restore()
    }

    private companion object {
        const val GAP_DP = 2f
    }
}

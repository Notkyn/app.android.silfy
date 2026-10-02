package ua.notky.silfy.ui.view.level

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import ua.notky.silfy.R
import ua.notky.silfy.models.states.WordState

/**
 * Knowledge level indicator: 4 vertical bars 5dp wide, heights 8/11/14/17dp.
 * Filled bars = level (0..4), painted with the level color.
 */
class LevelIndicatorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val barWidth = resources.getDimension(R.dimen.ds_level_bar_width)
    private val barGap = resources.getDimension(R.dimen.ds_level_bar_gap)
    private val barRadius = resources.getDimension(R.dimen.ds_level_bar_radius)
    private val barHeights = BAR_HEIGHTS_DP.map { it * resources.displayMetrics.density }
    private val emptyColor = ContextCompat.getColor(context, R.color.level_empty)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rect = RectF()

    var level: Int = 0
        set(value) {
            val newLevel = value.coerceIn(0, MAX_WORD_LEVEL)
            if (field == newLevel) return
            field = newLevel
            invalidate()
            contentDescription = newLevel.toString()
        }

    init {
        context.withStyledAttributes(attrs, R.styleable.LevelIndicatorView, defStyleAttr) {
            level = getInt(R.styleable.LevelIndicatorView_indicatorLevel, 0)
        }
    }

    fun setWordState(state: WordState?) {
        level = state?.level ?: 0
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val count = barHeights.size
        val width = (count * barWidth + (count - 1) * barGap).toInt() + paddingLeft + paddingRight
        val height = barHeights.last().toInt() + paddingTop + paddingBottom
        setMeasuredDimension(
            resolveSize(width, widthMeasureSpec),
            resolveSize(height, heightMeasureSpec)
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val filledColor = ContextCompat.getColor(context, levelColor(level))
        val bottom = (height - paddingBottom).toFloat()

        barHeights.forEachIndexed { index, barHeight ->
            val left = paddingLeft + index * (barWidth + barGap)
            rect.set(left, bottom - barHeight, left + barWidth, bottom)
            paint.color = if (index < level) filledColor else emptyColor
            canvas.drawRoundRect(rect, barRadius, barRadius, paint)
        }
    }

    private companion object {
        val BAR_HEIGHTS_DP = listOf(8f, 11f, 14f, 17f)
    }
}

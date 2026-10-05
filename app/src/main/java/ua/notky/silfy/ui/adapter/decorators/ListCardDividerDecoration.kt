package ua.notky.silfy.ui.adapter.decorators

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ua.notky.silfy.R

/**
 * 1dp line between rows inside a list card (#EEF0F6), drawn over the top edge of every row but the first.
 * Rows keep their size, so the card looks the same as LinearLayout with showDividers="middle".
 */
class ListCardDividerDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val height = context.resources.getDimension(R.dimen.ds_divider)
    private val paint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.card_divider)
    }

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        for (index in 0 until parent.childCount) {
            val child = parent.getChildAt(index)
            if (parent.getChildAdapterPosition(child) <= 0) continue

            val top = child.top + child.translationY
            canvas.drawRect(
                child.left.toFloat(),
                top,
                child.right.toFloat(),
                top + height,
                paint
            )
        }
    }
}

package ua.notky.silfy.ui.adapter.decorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 05.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class AddSpaceFirstItemDecorator(
    private val top: Int = 0
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)

        if (position == 0) {
            outRect.set(0, top, 0, 0)
        }
    }
}
package ua.notky.silfy.ui.adapter.decorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/** Equal gaps between the cells of a grid, none along its outer edges */
class GridSpacingItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) return
        val spanCount = (parent.layoutManager as? GridLayoutManager)?.spanCount ?: 1
        val column = position % spanCount

        outRect.left = column * spacing / spanCount
        outRect.right = spacing - (column + 1) * spacing / spanCount
        outRect.top = if (position >= spanCount) spacing else 0
    }
}

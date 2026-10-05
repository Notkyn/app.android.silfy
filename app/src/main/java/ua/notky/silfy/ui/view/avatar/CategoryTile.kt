package ua.notky.silfy.ui.view.avatar

import android.content.Context
import android.content.res.ColorStateList
import android.widget.TextView

/**
 * Category tile color: one of R.array.avatar_colors fixed by the category id,
 * so a category keeps its color when others are deleted. The first category gets the first color.
 */
fun categoryTileColor(context: Context, categoryId: Int?): Int {
    return AvatarView.avatarColor(context, (categoryId ?: 1) - 1)
}

/** Letter tile of a category: the first letter of the name on the category color (background — ds_bg_tile*) */
fun TextView.setCategoryTile(categoryId: Int?, title: String) {
    text = AvatarView.initialOf(title)
    backgroundTintList = ColorStateList.valueOf(categoryTileColor(context, categoryId))
}

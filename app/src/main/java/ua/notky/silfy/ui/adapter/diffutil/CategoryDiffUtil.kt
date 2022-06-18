package ua.notky.silfy.ui.adapter.diffutil

import ua.notky.base.ui.adapter.diffutils.BaseDiffUtilCallback
import ua.notky.silfy.models.model.Category

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 18.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class CategoryDiffUtil : BaseDiffUtilCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id && oldItem.title == newItem.title && oldItem.words == newItem.words
    }
}
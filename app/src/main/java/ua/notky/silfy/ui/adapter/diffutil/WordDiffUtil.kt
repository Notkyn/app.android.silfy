package ua.notky.silfy.ui.adapter.diffutil

import ua.notky.base.ui.adapter.diffutils.BaseDiffUtilCallback
import ua.notky.silfy.models.model.Word

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 05.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class WordDiffUtil : BaseDiffUtilCallback<Word>() {
    override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem.id == newItem.id
                && oldItem.en == newItem.en
                && oldItem.ua == newItem.ua
                && oldItem.isFavourite == newItem.isFavourite
                && oldItem.isBlacklist == newItem.isBlacklist
    }
}
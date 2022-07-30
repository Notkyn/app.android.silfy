package ua.notky.silfy.ui.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import ua.notky.silfy.R

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 05.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
object ItemCategoryBindingAdapter {

    @JvmStatic
    @BindingAdapter("setCountWords")
    fun bindingCountWords(view: TextView, count: Int?) {
        val countWords =  count ?: 0

        val pattern = when (countWords) {
            0 -> view.context.resources.getString(R.string.plurals_word_zero)
            1 -> view.context.resources.getString(R.string.plurals_word_one)
            2 -> view.context.resources.getString(R.string.plurals_word_two)
            3, 4 -> view.context.resources.getString(R.string.plurals_word_few)
            else -> view.context.resources.getString(R.string.plurals_word_many)
        }

        view.text = pattern.format(countWords)
    }
}
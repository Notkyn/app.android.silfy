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
        val pattern = view.context.resources.getQuantityString(
            R.plurals.pattern_count_words,
            count ?: 0
        )
        view.text = String.format(pattern, count ?: 0)
    }
}
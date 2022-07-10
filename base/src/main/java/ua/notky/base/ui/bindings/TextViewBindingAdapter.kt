package ua.notky.base.ui.bindings

import android.widget.TextView
import androidx.databinding.BindingAdapter

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 21.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
object TextViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("textById")
    fun bindingTextById(view: TextView, id: Int?) {
        try {
            id?.let { view.text = view.context.getString(it) }
        } catch (ex: Exception) {
            view.text = ""
        }
    }
}
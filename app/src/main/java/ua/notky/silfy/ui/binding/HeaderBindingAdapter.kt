package ua.notky.silfy.ui.binding

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import ua.notky.silfy.R
import ua.notky.silfy.models.states.EditableState

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 23.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

object HeaderBindingAdapter {

    @JvmStatic
    @BindingAdapter("visible_edit")
    fun bindingVisibleEdit(view: View, state: EditableState?) {
        state?.let {
            when (it) {
                EditableState.EDIT -> view.visibility = View.VISIBLE
                else -> view.visibility = View.GONE
            }
        }
    }

    @JvmStatic
    @BindingAdapter("text_title")
    fun bindingTextTitle(view: TextView, state: EditableState?) {
        state?.let {
            when (it) {
                EditableState.EDIT -> view.text = view.resources.getString(R.string.text_word_edit)
                EditableState.NEW -> view.text = view.resources.getString(R.string.tex_word_new)
            }
        }
    }
}
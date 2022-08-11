package ua.notky.silfy.ui.binding.words

import android.widget.TextView
import androidx.databinding.BindingAdapter
import ua.notky.silfy.models.states.WordState

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
object WordStatusBarBindingAdapter {

    @JvmStatic
    @BindingAdapter("set_word_state")
    fun bindingSetWordState(view: TextView, state: WordState?) {
        state?.let {
            view.text = view.context.getString(it.title)
        }
    }
}
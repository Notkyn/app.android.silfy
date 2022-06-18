package ua.notky.silfy.ui.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import ua.notky.silfy.R
import ua.notky.silfy.models.enums.WordFormType
import ua.notky.silfy.models.observable.FormWordModel
import ua.notky.silfy.ui.layout.word.FormEnterWordLayout

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 18.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
object FormWordBindingAdapter {

    @JvmStatic
    @BindingAdapter("set_type_title")
    fun bindingSetTypeTitle(view: TextView, type: WordFormType?) {
        val text = when (type) {
            WordFormType.TRANSLATE -> view.context.getString(R.string.text_translate)
            WordFormType.WORD -> view.context.getString(R.string.text_word)
            else -> ""
        }

        view.text = text
    }

    @JvmStatic
    @BindingAdapter("set_type_describe")
    fun bindingSetTypeDescribe(view: TextView, type: WordFormType?) {
        val text = when (type) {
            WordFormType.TRANSLATE -> view.context.getString(R.string.text_word_ua_version)
            WordFormType.WORD -> view.context.getString(R.string.text_word_en_version)
            else -> ""
        }

        view.text = text
    }

    @JvmStatic
    @BindingAdapter("set_type")
    fun bindingSetType(view: FormEnterWordLayout, type: WordFormType?) {
        type?.let { view.setType(it) }
    }
}
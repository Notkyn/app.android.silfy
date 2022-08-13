package ua.notky.silfy.ui.binding.menu

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import ua.notky.silfy.R
import ua.notky.silfy.models.enums.DictionaryCardType
import ua.notky.silfy.models.model.DictionaryInfo
import ua.notky.silfy.models.states.WordState
import ua.notky.silfy.ui.layout.menu.DictionaryCardLayout

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 29.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
object DictionaryMenuBindingAdapter {

    @JvmStatic
    @BindingAdapter("set_card_type")
    fun bindingSetCardType(view: DictionaryCardLayout, type: DictionaryCardType?) {
        type?.let { view.setType(it) }
    }

    @JvmStatic
    @BindingAdapter("card_title")
    fun bindingCardTitle(view: TextView, type: DictionaryCardType?) {
        type?.let {
            val text = try {
                view.context.getString(it.title)
            } catch (sx: Exception) {
                ""
            }

            view.text = text
        }
    }

    @JvmStatic
    @BindingAdapter("card_content")
    fun bindingCardContent(view: DictionaryCardLayout, value: DictionaryInfo?) {
        value?.let { view.setContent(it) }
    }

    @JvmStatic
    @BindingAdapter("card_list_type")
    fun bindingCardListType(view: ImageView, type: DictionaryCardType?) {
        type?.let {
            when (it) {
                DictionaryCardType.FAVOURITE -> {
                    view.setImageResource(R.drawable.ic_favourites_checked)
                    view.visibility = View.VISIBLE
                }
                DictionaryCardType.BLACK -> {
                    view.setImageResource(R.drawable.ic_blacklist_checked)
                    view.visibility = View.VISIBLE
                }
                else -> view.visibility = View.GONE
            }
        }
    }

    @JvmStatic
    @BindingAdapter("card_state_type")
    fun bindingCardStateType(view: ImageView, type: WordState?) {
        type?.let { view.setImageResource(it.image) }
    }
}
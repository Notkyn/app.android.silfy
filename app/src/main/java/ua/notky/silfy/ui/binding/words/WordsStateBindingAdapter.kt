package ua.notky.silfy.ui.binding.words

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import ua.notky.silfy.R

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */
object WordsStateBindingAdapter {

    @JvmStatic
    @BindingAdapter("checked_favourites")
    fun bindingFavouritesState(view: ImageView, state: Boolean?) {
        state?.let {
            if (it) {
                view.setImageResource(R.drawable.ic_tab_favourites_selected)
            } else {
                view.setImageResource(R.drawable.ic_favourites_unchecked)
            }
        }
    }
}
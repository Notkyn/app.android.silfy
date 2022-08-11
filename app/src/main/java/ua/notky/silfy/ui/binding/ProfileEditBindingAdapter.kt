package ua.notky.silfy.ui.binding

import android.widget.Button
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import ua.notky.silfy.R

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
object ProfileEditBindingAdapter {

    @JvmStatic
    @BindingAdapter("set_avatar")
    fun bindingSetAvatar(view: ImageView, value: String?) {
        if(!value.isNullOrEmpty()) {
            Picasso.get()
                .load(value)
                .transform(CropCircleTransformation())
                .placeholder(R.drawable.bg_avatar_placeholder)
                .error(R.drawable.bg_avatar_placeholder)
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("set_avatar_dark")
    fun bindingSetAvatarDark(view: ImageView, value: String?) {
        if(!value.isNullOrEmpty()) {
            Picasso.get()
                .load(value)
                .transform(CropCircleTransformation())
                .placeholder(R.drawable.bg_avatar_placeholder_dark)
                .error(R.drawable.bg_avatar_placeholder_dark)
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("switch_button")
    fun bindingSetAvatar(view: Button, value: String?) {
        val textId = if (value.isNullOrEmpty()) {
            R.string.button_select
        } else {
            R.string.button_change
        }

        view.text = view.context.getString(textId)
    }
}
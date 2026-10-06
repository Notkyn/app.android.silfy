package ua.notky.silfy.ui.binding.menu

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import ua.notky.silfy.R
import ua.notky.silfy.util.TimeUtils

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 25.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
object ProfileMenuBindingAdapter {

    @JvmStatic
    @BindingAdapter("setRegisterTime")
    fun bindingSetRegisterTime(view: TextView, value: Long?) {
        value?.let {
            val text = TimeUtils.format(TimeUtils.PATTERN_DATE, it)

            view.text = text
        }
    }

    /** Old menu profile list (item_menu_profile), until the menu moves to Silfy 2.0 */
    @JvmStatic
    @BindingAdapter("set_avatar_dark")
    fun bindingSetAvatarDark(view: ImageView, value: String?) {
        if (!value.isNullOrEmpty()) {
            Picasso.get()
                .load(value)
                .transform(CropCircleTransformation())
                .placeholder(R.drawable.bg_avatar_placeholder_dark)
                .error(R.drawable.bg_avatar_placeholder_dark)
                .into(view)
        }
    }
}
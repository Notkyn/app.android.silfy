package ua.notky.silfy.ui.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
object ProfileBindingAdapter {

    @JvmStatic
    @BindingAdapter("set_avatar")
    fun bindingSetAvatar(view: ImageView, uri: String?) {
        uri?.let {
            Glide.with(view)
                .load(it)
                .into(view)
        }
    }
}
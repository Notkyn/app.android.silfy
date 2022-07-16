package ua.notky.silfy.ui.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import ua.notky.silfy.R

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
object ProfileBindingAdapter {

    @JvmStatic
    @BindingAdapter("set_avatar")
    fun bindingSetAvatar(view: ImageView, uri: String?) {
        if(!uri.isNullOrEmpty()) {
            Picasso.get()
                .load(uri)
                .transform(CropCircleTransformation())
                .placeholder(R.drawable.bg_avatar_placeholder)
                .error(R.drawable.bg_avatar_placeholder)
                .into(view)
        }
    }
}
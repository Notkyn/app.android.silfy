package ua.notky.base.ui.bindings

import android.widget.ImageView
import androidx.databinding.BindingAdapter

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 22.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */
object ImageViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("src_image")
    fun bindingSrcImage(view: ImageView, resImage: Int?) {
        resImage?.let { view.setImageResource(it) }
    }
}
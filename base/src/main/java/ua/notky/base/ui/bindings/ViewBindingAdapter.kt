package ua.notky.base.ui.bindings

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 22.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */
object ViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("gone")
    fun bindingGoneView(view: View, state: Boolean?) {
        state?.let {
            view.visibility = if (it) View.VISIBLE else View.GONE
        }
    }
}
package ua.notky.silfy.ui.binding.menu

import android.widget.TextView
import androidx.databinding.BindingAdapter
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

    @JvmStatic
    @BindingAdapter("setFirstName", "setLastName")
    fun bindingSetFullName(view: TextView, first: String?, last: String?) {
        val text = "${first ?: ""} ${last ?: ""}"

        view.text = text
    }
}
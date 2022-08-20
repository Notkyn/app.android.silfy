package ua.notky.silfy.ui.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import ua.notky.silfy.models.enums.ProfileInfoType
import ua.notky.silfy.ui.layout.profile.ProfileInfoLayout
import ua.notky.silfy.util.TimeUtils
import ua.notky.silfy.util.TimeUtils.PATTERN_DATE

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 21.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
object ProfileInfoBindingAdapter {

    @JvmStatic
    @BindingAdapter("check_type", "value")
    fun bindingCheckType(view: TextView, type: ProfileInfoType?, text: Any?) {
        type?.let {

            val result = when(it) {
                ProfileInfoType.EMAIL -> text as String
                ProfileInfoType.SIGN_UP_TIME -> TimeUtils.format(PATTERN_DATE, text as Long)
            }

            view.text = result
        }
    }

    @JvmStatic
    @BindingAdapter("set_type")
    fun bindingSetType(view: ProfileInfoLayout, type: ProfileInfoType?) {
        type?.let { view.setType(it) }
    }

    @JvmStatic
    @BindingAdapter("set_value")
    fun bindingSetValue(view: ProfileInfoLayout, value: Any?) {
        value?.let { view.setValue(it) }
    }
}
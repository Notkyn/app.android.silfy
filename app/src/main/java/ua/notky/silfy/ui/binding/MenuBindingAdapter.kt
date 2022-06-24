package ua.notky.silfy.ui.binding

import androidx.databinding.BindingAdapter
import ua.notky.silfy.models.enums.MenuButtonType
import ua.notky.silfy.ui.layout.menu.MenuButtonLayout

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
object MenuBindingAdapter {

    @JvmStatic
    @BindingAdapter("setButtonMenuType")
    fun bindingSetButtonMenuType(view: MenuButtonLayout, type: MenuButtonType?) {
        type?.let {
            val text = try {
                view.context.getString(it.title)
            } catch (sx: Exception) {
                ""
            }

            view.setTitle(text)
        }
    }
}
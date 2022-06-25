package ua.notky.silfy.ui.binding.menu

import androidx.databinding.BindingAdapter
import ua.notky.silfy.models.enums.MenuButtonType
import ua.notky.silfy.models.enums.MenuHeaderType
import ua.notky.silfy.ui.layout.menu.MenuButtonLayout
import ua.notky.silfy.ui.layout.menu.MenuHeaderLayout

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

    @JvmStatic
    @BindingAdapter("setMenuHeaderType")
    fun bindingSetMenuHeaderType(view: MenuHeaderLayout, type: MenuHeaderType?) {
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
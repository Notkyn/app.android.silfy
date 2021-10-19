package ua.notky.silfy.ui.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import ua.notky.base.extension.setImageTint
import ua.notky.silfy.R
import ua.notky.silfy.model.enums.SortLang
import ua.notky.silfy.model.enums.SortState
import ua.notky.silfy.model.enums.SortType

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */
object SortBindingAdapter {

    @JvmStatic
    @BindingAdapter("sortEn")
    fun bindingSortEn(view: ImageView, state: SortLang?) {
        state?.let {
            when (it) {
                SortLang.EN_DOWN -> {
                    view.setImageTint(R.color.sort_enable)
                    view.setImageResource(it.image)
                }
                SortLang.EN_UP -> {
                    view.setImageTint(R.color.sort_enable)
                    view.setImageResource(it.image)
                }
                else -> view.setImageTint(R.color.sort_disable)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("sortRu")
    fun bindingSortRu(view: ImageView, state: SortLang?) {
        state?.let {
            when (it) {
                SortLang.RU_DOWN -> {
                    view.setImageTint(R.color.sort_enable)
                    view.setImageResource(it.image)
                }
                SortLang.RU_UP -> {
                    view.setImageTint(R.color.sort_enable)
                    view.setImageResource(it.image)
                }
                else -> view.setImageTint(R.color.sort_disable)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("sortType")
    fun bindingSortType(view: ImageView, state: SortType?) {
        state?.let { view.setImageResource(it.image) }
    }

    @JvmStatic
    @BindingAdapter("sortState")
    fun bindingSortState(view: ImageView, state: SortState?) {
        state?.let { view.setImageResource(it.image) }
    }
}
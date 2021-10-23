package ua.notky.silfy.ui.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import ua.notky.base.extension.setImageTint
import ua.notky.silfy.R
import ua.notky.silfy.models.states.SortLang

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */
object SortBindingAdapter {

    @JvmStatic
    @BindingAdapter("sort_en")
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
    @BindingAdapter("sort_ru")
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
}
package ua.notky.silfy.ui.binding.go

import androidx.databinding.BindingAdapter
import ua.notky.silfy.models.observable.GoModel
import ua.notky.silfy.ui.layout.go.GoFooterLayout

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 09.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
object GoFooterBindingAdapter {

    @JvmStatic
    @BindingAdapter("setModel")
    fun bindingSetModel(view: GoFooterLayout, model: GoModel?) {
        model?.let { view.setModel(it) }
    }
}
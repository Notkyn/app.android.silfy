package ua.notky.silfy.ui.binding

import androidx.databinding.BindingAdapter
import ua.notky.silfy.models.observable.CategoryOverviewModel
import ua.notky.silfy.ui.layout.HeaderCategoryOverviewLayout

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 18.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
object CategoryOverviewBindingAdapter {

    @JvmStatic
    @BindingAdapter("set_model")
    fun bindingSetModel(view: HeaderCategoryOverviewLayout, model: CategoryOverviewModel?) {
        model?.let { view.setModel(it) }
    }
}
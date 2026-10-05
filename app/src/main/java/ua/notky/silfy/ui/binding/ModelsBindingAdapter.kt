package ua.notky.silfy.ui.binding

import androidx.databinding.BindingAdapter
import ua.notky.silfy.models.observable.CategoryOverviewModel
import ua.notky.silfy.models.observable.ProfileModel
import ua.notky.silfy.ui.layout.category.CategoryInfoLayout
import ua.notky.silfy.ui.layout.category.HeaderCategoryOverviewLayout
import ua.notky.silfy.ui.layout.profile.ProfileHeaderLayout

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
object ModelsBindingAdapter {

    @JvmStatic
    @BindingAdapter("set_model")
    fun bindingSetModel(view: HeaderCategoryOverviewLayout, model: CategoryOverviewModel?) {
        model?.let { view.setModel(it) }
    }

    @JvmStatic
    @BindingAdapter("set_model")
    fun bindingSetModel(view: CategoryInfoLayout, model: CategoryOverviewModel?) {
        model?.let { view.setModel(it) }
    }

    @JvmStatic
    @BindingAdapter("set_model")
    fun bindingSetModel(view: ProfileHeaderLayout, model: ProfileModel?) {
        model?.let { view.setModel(it) }
    }
}
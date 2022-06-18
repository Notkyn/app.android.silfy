package ua.notky.silfy.ui.binding

import androidx.databinding.BindingAdapter
import ua.notky.silfy.models.observable.CategoryOverviewModel
import ua.notky.silfy.models.observable.FormWordModel
import ua.notky.silfy.models.observable.WordsModel
import ua.notky.silfy.ui.layout.category.CategoryInfoLayout
import ua.notky.silfy.ui.layout.category.HeaderCategoryOverviewLayout
import ua.notky.silfy.ui.layout.word.FormEnterWordLayout
import ua.notky.silfy.ui.layout.word.WordStatusBarLayout

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
    fun bindingSetModel(view: FormEnterWordLayout, model: FormWordModel?) {
        model?.let { view.setModel(it) }
    }

    @JvmStatic
    @BindingAdapter("set_model")
    fun bindingSetModel(view: WordStatusBarLayout, model: WordsModel?) {
        model?.let { view.setModel(it) }
    }
}
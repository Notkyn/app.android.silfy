package ua.notky.silfy.viewmodel.category

import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.models.observable.CategoryOverviewModel
import ua.notky.silfy.tools.WordSort

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 15.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

class CategoryOverviewViewModel : BaseViewModel() {
    val model = CategoryOverviewModel()

    fun updateSelectedCategory(category: Category) {
        model.id = category.id
        model.title.set(category.title)
        model.words.set(category.words)
    }

    fun onSortWords(params: WordSort.Params) {
        model.words.get()?.let {
            model.words.set(
                WordSort.sort(it, params)
            )
        }
    }
}
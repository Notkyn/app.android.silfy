package ua.notky.silfy.viewmodel.category

import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.models.model.Category

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 09.11.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class CategoryViewModel : BaseViewModel() {

    fun getCategoriesForWord(id: Int): List<Category> {
        return listOf(
            Category(10, "Category 5", listOf()),
            Category(11, "Category 5", listOf()),
            Category(12, "Category 5", listOf())
        )
    }
}
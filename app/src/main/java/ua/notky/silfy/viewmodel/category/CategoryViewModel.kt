package ua.notky.silfy.viewmodel.category

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.usecase.category.LoadAllCategoryUseCase
import javax.inject.Inject

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 05.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val loadAllCategoryUseCase: LoadAllCategoryUseCase
) : BaseViewModel() {
    val categories = loadAllCategoryUseCase.categories

    fun fetchData(categories: List<Category>? = null) {
        viewModelScope.launch {
            val params = LoadAllCategoryUseCase.Params(categories)
            loadAllCategoryUseCase.load(params)
        }
    }

    fun getNamesAllCategories(): List<String> {
        return categories.value?.map { it.title } ?: listOf()
    }
}
package ua.notky.silfy.viewmodel.category

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.repository.CategoryRepository
import javax.inject.Inject

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 05.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : BaseViewModel() {
    val categories = categoryRepository.category

    fun fetchData(categories: List<Category>? = null) {
        viewModelScope.launch {
            categoryRepository.loadAll(categories)
        }
    }

    fun getNamesAllCategories(): List<String> {
        return categories.value?.map { it.title } ?: listOf()
    }
}
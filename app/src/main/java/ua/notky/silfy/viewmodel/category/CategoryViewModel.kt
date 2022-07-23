package ua.notky.silfy.viewmodel.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.usecase.category.LoadAllCategoriesUseCase
import javax.inject.Inject

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 05.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val loadAllCategoriesUseCase: LoadAllCategoriesUseCase
) : BaseViewModel() {
    private val _categories: MutableLiveData<List<Category>> = MutableLiveData()
    val categories: LiveData<List<Category>> = _categories

    fun fetchData(categories: List<Category>? = null) {
        viewModelScope.launch {
            val result = loadAllCategoriesUseCase.load()
            if (result.isSuccess) {
                handleResultLoading(result.getOrNull() ?: listOf(), categories)
            } else {
                _categories.postValue(listOf())
            }
        }
    }

    private fun handleResultLoading(actual: List<Category>, filter: List<Category>?) {
        val filterIds = filter?.map { it.id } ?: listOf()

        _categories.postValue(actual.filter { !filterIds.contains(it.id) })
    }

    fun getNamesAllCategories(): List<String> {
        return _categories.value?.map { it.title } ?: listOf()
    }

    @Deprecated("without refresh data from database")
    fun refreshCategories(category: Category) {
        val categories: MutableList<Category> = mutableListOf()
        _categories.value?.let { categories.addAll(it) }
        categories.add(category)
        _categories.postValue(categories)
    }
}
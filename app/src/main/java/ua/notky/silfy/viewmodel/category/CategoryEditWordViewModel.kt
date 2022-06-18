package ua.notky.silfy.viewmodel.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.util.help.getTempCategories

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 09.11.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class CategoryEditWordViewModel : BaseViewModel() {
    private val _categoriesForEditWord: MutableLiveData<List<Category>> = MutableLiveData()
    val categoriesForEditWord: LiveData<List<Category>> = _categoriesForEditWord

    fun onLoadCategoriesForEditWord(id: Int?) {
        id?.let { _categoriesForEditWord.postValue(getTempCategories(10)) }
    }

    fun onDeleteCategoryForEditWord(category: Category) {
        _categoriesForEditWord.postValue(
            _categoriesForEditWord.value?.filter { it.id != category.id }
        )
    }
}
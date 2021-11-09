package ua.notky.silfy.viewmodel.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.models.model.Category

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 09.11.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class CategoryViewModel : BaseViewModel() {
    private val _categoriesForEditWord: MutableLiveData<List<Category>> = MutableLiveData()

    fun getCategoriesForEditWordLiveData(): LiveData<List<Category>> {
        return _categoriesForEditWord
    }

    fun onLoadCategoriesForEditWord(id: Int?) {
        id?.let {
            _categoriesForEditWord.postValue(listOf(
                Category(10, "Category 10", listOf()),
                Category(11, "Category 11", listOf()),
                Category(12, "Category 12", listOf()),
                Category(13, "Category 13", listOf()),
                Category(14, "Category 14", listOf()),
                Category(15, "Category 15", listOf()),
                Category(16, "Category 16", listOf()),
                Category(17, "Category 17", listOf()),
                Category(18, "Category 18", listOf()),
                Category(19, "Category 19", listOf()),
                Category(20, "Category 20", listOf())
            ))
        }
    }
}
package ua.notky.silfy.viewmodel
.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ua.notky.base.validation.ValidationModel
import ua.notky.base.validation.ValidationService
import ua.notky.base.viewmodel.BaseValidationViewModel
import ua.notky.silfy.config.VALIDATION_CATEGORY_IS_EXIST
import ua.notky.silfy.config.VALIDATION_CATEGORY_NAME
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.models.observable.NewCategoryModel
import ua.notky.silfy.util.help.getTempCategory
import javax.inject.Inject

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 15.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@HiltViewModel
class NewCategoryViewModel @Inject constructor(
    override val validation: ValidationService
) : BaseValidationViewModel() {
    val model = NewCategoryModel()

    private val _newCategory: MutableLiveData<Category> = MutableLiveData()
    val newCategory: LiveData<Category> = _newCategory

    fun clearData() {
        _newCategory.postValue(null)
        model.name.set("")
    }

    fun onCreateCategory(names: List<String>) {
        if (isValidName(names)) {
            _newCategory.postValue(getTempCategory(model.name.get() ?: ""))
        }
    }

    private fun isValidName(names: List<String>): Boolean {
        return addValidateData(
            listOf(
                ValidationModel(
                    VALIDATION_CATEGORY_NAME,
                    expect = model.name.get()
                ),
                ValidationModel(
                    VALIDATION_CATEGORY_IS_EXIST,
                    expect = model.name.get(),
                    contains = names
                )
            )
        )
    }
}
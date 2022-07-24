package ua.notky.silfy.viewmodel.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.notky.base.validation.ValidationModel
import ua.notky.base.validation.ValidationService
import ua.notky.base.viewmodel.BaseValidationViewModel
import ua.notky.silfy.config.VALIDATION_CATEGORY_IS_EXIST
import ua.notky.silfy.config.VALIDATION_CATEGORY_NAME
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.models.observable.EditCategoryModel
import ua.notky.silfy.models.states.CategorySaveUiState
import ua.notky.silfy.usecase.category.SaveCategoryUseCase
import javax.inject.Inject

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 15.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@HiltViewModel
class EditCategoryViewModel @Inject constructor(
    override val validation: ValidationService,
    private val saveCategoryUseCase: SaveCategoryUseCase
) : BaseValidationViewModel() {
    val model = EditCategoryModel()

    private val _uiState: MutableLiveData<CategorySaveUiState> = MutableLiveData()
    val uiState: LiveData<CategorySaveUiState> = _uiState

    fun onSelectCategory(category: Category? = null) {
        _uiState.postValue(CategorySaveUiState.Checking)
        if (category != null) {
            model.id = category.id
            model.title = category.title
            model.name.set(category.title)
        } else {
            model.id = null
            model.title = null
            model.name.set("")
        }
    }

    fun onSaveCategory(names: List<String>) {
        if (model.id != null) {
            saveCategory(names.filter { it != model.title })
        } else {
            saveCategory(names)
        }
    }

    private fun saveCategory(names: List<String>) {
        viewModelScope.launch {
            if (isValidName(names)) {
                _uiState.postValue(CategorySaveUiState.Saving)

                val params = SaveCategoryUseCase.Params(
                    model.id,
                    model.name.get()
                )

                if (saveCategoryUseCase.save(params).isSuccess) {
                    _uiState.postValue(CategorySaveUiState.Saved)
                } else {
                    _uiState.postValue(CategorySaveUiState.Failure)
                }
            }
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
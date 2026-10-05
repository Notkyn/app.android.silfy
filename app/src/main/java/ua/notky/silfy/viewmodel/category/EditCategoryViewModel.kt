package ua.notky.silfy.viewmodel.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.models.model.CategoryNameForm
import ua.notky.silfy.models.states.CategorySaveUiState
import ua.notky.silfy.repository.db.dao.CategoryDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import ua.notky.silfy.usecase.category.CategoryExistsException
import ua.notky.silfy.usecase.category.SaveCategoryUseCase
import ua.notky.silfy.util.normalizeCategoryName
import ua.notky.silfy.validation.checkCategoryName
import javax.inject.Inject

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 15.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

/**
 * 3c New / Edit category sheet. Scoped to the sheet; the category comes from its `categoryId` argument
 * (-1 — new category). The name is checked when "Save" is pressed.
 */
@HiltViewModel
class EditCategoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dataStore: AppDataStorePreferences,
    private val categoryDao: CategoryDao,
    private val saveCategoryUseCase: SaveCategoryUseCase
) : BaseViewModel() {

    private val categoryId: Int? = savedStateHandle.get<Int>(ARG_CATEGORY_ID)?.takeIf { it != NEW_CATEGORY_ID }

    private val _form = MutableLiveData<CategoryNameForm>()
    val form: LiveData<CategoryNameForm> = _form

    private val _uiState = MutableLiveData<CategorySaveUiState>(CategorySaveUiState.Idle)
    val uiState: LiveData<CategorySaveUiState> = _uiState

    init {
        viewModelScope.launch {
            val profileId = dataStore.getProfileId()
            val category = if (categoryId != null && profileId != null) {
                categoryDao.getById(categoryId, profileId)
            } else {
                null
            }
            _form.value = CategoryNameForm(isNew = categoryId == null, name = category?.title.orEmpty())
        }
    }

    fun setName(value: String) {
        val current = _form.value ?: return
        if (current.name != value) _form.value = current.copy(name = value, error = null)
    }

    fun save() {
        val form = _form.value ?: return
        if (_uiState.value != CategorySaveUiState.Idle) return

        val name = normalizeCategoryName(form.name)
        val error = when {
            name.isEmpty() -> CategoryNameForm.Error.EMPTY
            !checkCategoryName(name) -> CategoryNameForm.Error.CHARS
            else -> null
        }
        if (error != null) {
            _form.value = form.copy(error = error)
            return
        }

        _uiState.value = CategorySaveUiState.Saving
        viewModelScope.launch {
            val result = saveCategoryUseCase.save(SaveCategoryUseCase.Params(categoryId, name))
            when {
                result.isSuccess -> _uiState.value = CategorySaveUiState.Saved
                result.exceptionOrNull() is CategoryExistsException -> {
                    _form.value = _form.value?.copy(error = CategoryNameForm.Error.EXISTS)
                    _uiState.value = CategorySaveUiState.Idle
                }
                else -> _uiState.value = CategorySaveUiState.Failure
            }
        }
    }

    /** Failures are shown once */
    fun consumeState() {
        _uiState.value = CategorySaveUiState.Idle
    }

    companion object {
        const val ARG_CATEGORY_ID = "categoryId"
        const val NEW_CATEGORY_ID = -1
    }
}

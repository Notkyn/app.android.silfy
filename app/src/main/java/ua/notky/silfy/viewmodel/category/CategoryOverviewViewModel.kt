package ua.notky.silfy.viewmodel.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.mapper.category.CategoryMapper
import ua.notky.silfy.mapper.word.WordMapper
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.models.model.Word
import ua.notky.silfy.models.states.CategoryDeleteUiState
import ua.notky.silfy.repository.db.dao.CategoryDao
import ua.notky.silfy.repository.db.dao.word.WordListDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import ua.notky.silfy.usecase.category.DeleteCategoryUseCase
import javax.inject.Inject

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 15.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

/**
 * 3b Category and its 3d "Delete this category?". Scoped to CategoryOverviewFragment;
 * the category comes from the `categoryId` navigation argument. Live: a rename or an edited word shows up at once.
 */
@HiltViewModel
class CategoryOverviewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dataStore: AppDataStorePreferences,
    private val categoryDao: CategoryDao,
    private val wordListDao: WordListDao,
    private val deleteCategoryUseCase: DeleteCategoryUseCase
) : BaseViewModel() {

    val categoryId: Int = requireNotNull(savedStateHandle.get<Int>(ARG_CATEGORY_ID)) { "categoryId is missing" }

    private val profileId = MutableLiveData<Int>()

    /** null once the category is gone */
    val category: LiveData<Category?> = profileId.switchMap { id ->
        categoryDao.getLiveDataById(categoryId, id).map { local -> local?.let { CategoryMapper.map(it) } }
    }

    val words: LiveData<List<Word>> = profileId.switchMap { id ->
        wordListDao.getCategoryWords(id, categoryId).map { WordMapper.map(it) }
    }

    private val _uiState = MutableLiveData<CategoryDeleteUiState>(CategoryDeleteUiState.Idle)
    val uiState: LiveData<CategoryDeleteUiState> = _uiState

    init {
        viewModelScope.launch {
            dataStore.getProfileId()?.let { profileId.value = it }
        }
    }

    fun delete() {
        if (_uiState.value != CategoryDeleteUiState.Idle) return
        _uiState.value = CategoryDeleteUiState.Deleting

        viewModelScope.launch {
            val result = deleteCategoryUseCase.delete(DeleteCategoryUseCase.Params(categoryId))
            _uiState.value = if (result.isSuccess) CategoryDeleteUiState.Deleted else CategoryDeleteUiState.Failure
        }
    }

    /** Failures are shown once */
    fun consumeState() {
        _uiState.value = CategoryDeleteUiState.Idle
    }

    private companion object {
        /** Safe Args name in nav_graph_main */
        const val ARG_CATEGORY_ID = "categoryId"
    }
}

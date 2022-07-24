package ua.notky.silfy.viewmodel.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.models.model.Word
import ua.notky.silfy.models.observable.CategoryOverviewModel
import ua.notky.silfy.models.states.CategoryDeleteUiState
import ua.notky.silfy.usecase.category.DeleteCategoryUseCase
import ua.notky.silfy.usecase.category.LoadCategoryWithWordsUseCase
import ua.notky.silfy.util.WordSort
import javax.inject.Inject

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 15.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@HiltViewModel
class CategoryOverviewViewModel @Inject constructor(
    private val loadCategoryWithWordsUseCase: LoadCategoryWithWordsUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase
) : BaseViewModel() {
    val model = CategoryOverviewModel()

    val category = loadCategoryWithWordsUseCase.category

    private val _words: MutableLiveData<List<Word>> = MutableLiveData()
    val words: LiveData<List<Word>> = _words

    private val _uiState: MutableLiveData<CategoryDeleteUiState> = MutableLiveData()
    val uiState: LiveData<CategoryDeleteUiState> = _uiState

    fun clearState() {
        _uiState.postValue(CategoryDeleteUiState.Normal)
    }

    fun getSelectedCategory(): Category {
        return Category(
            model.id,
            model.title.get() ?: "",
            _words.value ?: listOf()
        )
    }

    fun onSelectCategory(id: Int) {
        viewModelScope.launch {
            val params = LoadCategoryWithWordsUseCase.Params(id)
            loadCategoryWithWordsUseCase.load(params)
        }
    }

    fun updateUiModel(category: Category) {
        model.id = category.id
        model.title.set(category.title)
        model.wordsSize.set(category.words.size)
        _words.postValue(category.words)
    }

    fun onSortWords(params: WordSort.Params) {
        _words.postValue(
            WordSort.sort(_words.value ?: listOf(), params)
        )
    }

    fun onDelete() {
        viewModelScope.launch {
            _uiState.postValue(CategoryDeleteUiState.Deleting)

            val params = DeleteCategoryUseCase.Params(model.id)

            if (deleteCategoryUseCase.delete(params).isSuccess) {
                _uiState.postValue(CategoryDeleteUiState.Deleted)
            } else {
                _uiState.postValue(CategoryDeleteUiState.Failure)
            }
        }
    }
}
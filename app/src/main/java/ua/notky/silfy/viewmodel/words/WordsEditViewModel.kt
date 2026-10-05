package ua.notky.silfy.viewmodel.words

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.mapper.category.CategoryMapper
import ua.notky.silfy.models.enums.AppLanguage
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.models.model.Word
import ua.notky.silfy.models.model.WordForm
import ua.notky.silfy.models.states.EditWordUiState
import ua.notky.silfy.models.states.ResultLoadWordWithCategories
import ua.notky.silfy.models.states.WordState
import ua.notky.silfy.repository.db.dao.CategoryDao
import ua.notky.silfy.repository.db.dao.ProfileDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import ua.notky.silfy.usecase.word.DeleteWordUseCase
import ua.notky.silfy.usecase.word.LoadWordWithCategoriesUseCase
import ua.notky.silfy.usecase.word.SaveWordUseCase
import ua.notky.silfy.usecase.word.WordExistsException
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 23.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

/**
 * 2c Edit / New word and its 2d "Add to categories" sheet.
 * Scoped to WordsEditFragment; the word comes from the `wordId` navigation argument (-1 — new word).
 */
@HiltViewModel
class WordsEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dataStore: AppDataStorePreferences,
    private val profileDao: ProfileDao,
    private val categoryDao: CategoryDao,
    private val loadWordWithCategoriesUseCase: LoadWordWithCategoriesUseCase,
    private val saveWordUseCase: SaveWordUseCase,
    private val deleteWordUseCase: DeleteWordUseCase
) : BaseViewModel() {

    private val wordId: Int? = savedStateHandle.get<Int>(ARG_WORD_ID)?.takeIf { it != NEW_WORD_ID }

    /** The word as it is saved: keeps the exact points when the level is not changed */
    private var savedWord: Word? = null

    private val _form = MutableLiveData<WordForm>()
    val form: LiveData<WordForm> = _form

    /** Profile language: badge next to "Translation" */
    private val _language = MutableLiveData<AppLanguage>()
    val language: LiveData<AppLanguage> = _language

    /** All categories of the profile for the "Add to categories" sheet */
    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val _uiState = MutableLiveData<EditWordUiState>(EditWordUiState.Idle)
    val uiState: LiveData<EditWordUiState> = _uiState

    init {
        viewModelScope.launch {
            loadProfileData()
            loadWord()
        }
    }

    private suspend fun loadProfileData() {
        val profileId = dataStore.getProfileId() ?: return
        profileDao.getById(profileId)?.let { _language.value = it.language }
        _categories.value = CategoryMapper.map(categoryDao.getAll(profileId))
    }

    private suspend fun loadWord() {
        if (wordId == null) {
            _form.value = WordForm(isNew = true)
            return
        }

        val params = LoadWordWithCategoriesUseCase.Params(wordId)
        when (val result = loadWordWithCategoriesUseCase.load(params)) {
            is ResultLoadWordWithCategories.Success -> {
                savedWord = result.word
                _form.value = WordForm(
                    isNew = false,
                    en = result.word.en,
                    translation = result.word.translation,
                    categories = result.categories,
                    isFavourite = result.word.isFavourite,
                    isBlacklist = result.word.isBlacklist,
                    state = result.word.state
                )
            }
            is ResultLoadWordWithCategories.Failure -> _uiState.value = EditWordUiState.Failure.Load
        }
    }

    fun setEn(value: String) {
        updateForm { if (it.en == value) it else it.copy(en = value, isDuplicate = false) }
    }

    fun setTranslation(value: String) {
        updateForm { if (it.translation == value) it else it.copy(translation = value) }
    }

    fun toggleFavourite() {
        updateForm { it.copy(isFavourite = !it.isFavourite) }
    }

    fun toggleBlacklist() {
        updateForm { it.copy(isBlacklist = !it.isBlacklist) }
    }

    fun resetLevel() {
        updateForm { it.copy(state = WordState.UNKNOWN) }
    }

    fun toggleCategory(category: Category) {
        updateForm { form ->
            val isSelected = form.categories.any { it.id == category.id }
            val categories = if (isSelected) {
                form.categories.filter { it.id != category.id }
            } else {
                form.categories + category
            }
            form.copy(categories = categories)
        }
    }

    fun removeCategory(category: Category) {
        updateForm { form -> form.copy(categories = form.categories.filter { it.id != category.id }) }
    }

    fun save() {
        val form = _form.value ?: return
        if (!form.canSave || _uiState.value != EditWordUiState.Idle) return
        _uiState.value = EditWordUiState.Saving

        viewModelScope.launch {
            val saved = savedWord
            val points = if (saved != null && saved.state == form.state) saved.minCountState else form.state.minCount

            val word = Word(
                id = wordId,
                en = form.en,
                translation = form.translation,
                state = form.state,
                minCountState = points,
                isFavourite = form.isFavourite,
                isBlacklist = form.isBlacklist
            )

            val result = saveWordUseCase.save(SaveWordUseCase.Params(word, form.categories))
            when {
                result.isSuccess -> _uiState.value = EditWordUiState.Saved
                result.exceptionOrNull() is WordExistsException -> {
                    updateForm { it.copy(isDuplicate = true) }
                    _uiState.value = EditWordUiState.Idle
                }
                else -> _uiState.value = EditWordUiState.Failure.Save
            }
        }
    }

    fun delete() {
        if (wordId == null || _uiState.value != EditWordUiState.Idle) return
        _uiState.value = EditWordUiState.Deleting

        viewModelScope.launch {
            val result = deleteWordUseCase.delete(DeleteWordUseCase.Params(wordId))
            _uiState.value = if (result.isSuccess) EditWordUiState.Deleted else EditWordUiState.Failure.Delete
        }
    }

    /** Failures are shown once */
    fun consumeState() {
        _uiState.value = EditWordUiState.Idle
    }

    private fun updateForm(update: (WordForm) -> WordForm) {
        val current = _form.value ?: return
        val updated = update(current)
        if (updated != current) _form.value = updated
    }

    private companion object {
        /** Safe Args name in nav_graph_main */
        const val ARG_WORD_ID = "wordId"
        const val NEW_WORD_ID = -1
    }
}

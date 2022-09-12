package ua.notky.silfy.viewmodel.words

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.notky.base.extension.addOnPropertyChanged
import ua.notky.base.validation.ValidationModel
import ua.notky.base.validation.ValidationService
import ua.notky.base.viewmodel.BaseValidationViewModel
import ua.notky.silfy.config.VALIDATION_WORD_EU
import ua.notky.silfy.config.VALIDATION_WORD_UA
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.models.model.Word
import ua.notky.silfy.models.observable.FormWordModel
import ua.notky.silfy.models.observable.WordsModel
import ua.notky.silfy.models.states.EditWordUiState
import ua.notky.silfy.models.states.ResultLoadWordWithCategories
import ua.notky.silfy.models.states.WordState
import ua.notky.silfy.usecase.word.DeleteWordUseCase
import ua.notky.silfy.usecase.word.LoadWordWithCategoriesUseCase
import ua.notky.silfy.usecase.word.SaveWordUseCase
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 23.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@HiltViewModel
class WordsEditViewModel @Inject constructor(
    override val validation: ValidationService,
    private val loadWordWithCategoriesUseCase: LoadWordWithCategoriesUseCase,
    private val deleteWordUseCase: DeleteWordUseCase,
    private val saveWordUseCase: SaveWordUseCase
) : BaseValidationViewModel() {
    val model: WordsModel = WordsModel()
    val wordModel = FormWordModel()
    val translateModel = FormWordModel()
    private var oldWord: Word? = null
    private var oldCategories: List<Category> = listOf()

    private val _categories: MutableLiveData<List<Category>> = MutableLiveData()
    val categories: LiveData<List<Category>> = _categories

    private val _uiState: MutableLiveData<EditWordUiState> = MutableLiveData()
    val uiState: LiveData<EditWordUiState> = _uiState

    fun selectWord(item: Word?) {
        viewModelScope.launch {
            clearModel()

            item?.let {
                loadData(it.id)
            } ?: run {
                oldWord = null
                oldCategories = listOf()
                _categories.postValue(listOf())
            }

            observersModels()
        }
    }

    private suspend fun loadData(wordId: Int?) {
        val params = LoadWordWithCategoriesUseCase.Params(wordId)
        when (val result = loadWordWithCategoriesUseCase.load(params)) {
            is ResultLoadWordWithCategories.Success -> {
                oldWord = result.word
                oldCategories = result.categories
                updateModel(result.word)
                _categories.postValue(result.categories)
            }
            is ResultLoadWordWithCategories.Failure ->
                _uiState.postValue(EditWordUiState.Failure.Load)
        }
    }

    private fun updateModel(word: Word) {
        model.id = word.id
        wordModel.value.set(word.en)
        translateModel.value.set(word.ua)
        model.isBlacklist.set(word.isBlacklist)
        model.isFavourite.set(word.isFavourite)
        model.state.set(word.state)
    }

    private fun observersModels() {
        wordModel.value.addOnPropertyChanged { checkChangedState() }
        translateModel.value.addOnPropertyChanged { checkChangedState() }
        model.isBlacklist.addOnPropertyChanged { checkChangedState() }
        model.isFavourite.addOnPropertyChanged { checkChangedState() }
        model.state.addOnPropertyChanged { checkChangedState() }
    }

    private fun clearModel() {
        model.id = null
        wordModel.value.set("")
        translateModel.value.set("")
        model.isBlacklist.set(false)
        model.isFavourite.set(false)
        model.state.set(WordState.UNKNOWN)
        model.isChanged.set(false)
    }

    fun checkChangedState() {
        val oldIds = oldCategories.map { it.id }.toSet()
        val actualIds = _categories.value?.map { it.id }?.toSet() ?: setOf()

        val isChangedList = oldIds.size != actualIds.size || !oldIds.containsAll(actualIds)

        model.isChanged.set(
            isChangedList
                    || oldWord?.en != wordModel.value.get()
                    || oldWord?.ua != translateModel.value.get()
                    || oldWord?.state != model.state.get()
                    || oldWord?.isFavourite != model.isFavourite.get()
                    || oldWord?.isBlacklist != model.isBlacklist.get()
        )
    }

    fun isNewWord(): Boolean {
        return oldWord == null
    }

    fun onChangeWordState() {
        when (model.state.get()) {
            WordState.UNKNOWN -> model.state.set(WordState.POOR)
            WordState.POOR -> model.state.set(WordState.AVERAGE)
            WordState.AVERAGE -> model.state.set(WordState.GOOD)
            WordState.GOOD -> model.state.set(WordState.EXCELLENT)
            WordState.EXCELLENT -> model.state.set(WordState.UNKNOWN)
            else -> model.state.set(WordState.POOR)
        }
    }

    fun onSaveWord() {
        viewModelScope.launch {
            _uiState.postValue(EditWordUiState.Saving)

            if (isValidWord()) {
                val count = when {
                    oldWord == null -> model.state.get()?.minCount
                    oldWord != null && oldWord?.state != model.state.get() -> model.state.get()?.minCount
                    else -> oldWord?.minCountState
                }

                val updatedWord = Word(
                    model.id,
                    wordModel.value.get() ?: "",
                    translateModel.value.get() ?: "",
                    model.state.get() ?: WordState.UNKNOWN,
                    count ?: WordState.UNKNOWN.minCount,
                    model.isFavourite.get(),
                    model.isBlacklist.get()
                )

                val params = SaveWordUseCase.Params(
                    updatedWord,
                    _categories.value ?: listOf()
                )

                if (saveWordUseCase.save(params).isSuccess) {
                    _uiState.postValue(EditWordUiState.Saved)
                } else {
                    _uiState.postValue(EditWordUiState.Failure.Save)
                }
            } else {
                _uiState.postValue(EditWordUiState.Normal)
            }
        }
    }

    fun onDeleteWord() {
        viewModelScope.launch {
            _uiState.postValue(EditWordUiState.Deleting)

            val params = DeleteWordUseCase.Params(oldWord?.id)

            if (deleteWordUseCase.delete(params).isSuccess) {
                _uiState.postValue(EditWordUiState.Deleted)
            } else {
                _uiState.postValue(EditWordUiState.Failure.Delete)
            }
        }
    }

    private fun isValidWord(): Boolean {
        return addValidateData(
            listOf(
                ValidationModel(VALIDATION_WORD_EU, wordModel.value.get()),
                ValidationModel(VALIDATION_WORD_UA, translateModel.value.get())
            )
        )
    }

    fun addCategory(category: Category) {
        if (_categories.value.isNullOrEmpty()) {
            _categories.postValue(listOf(category))
        } else {
            if (_categories.value?.none { it.id == category.id } == true) {
                _categories.value?.toMutableList()?.let {
                    it.add(category)
                    _categories.postValue(it)
                }
            }
        }
    }

    fun onDeleteCategoryFromWordList(category: Category) {
        _categories.postValue(_categories.value?.filter { it.id != category.id })
    }

    fun clearState() {
        _uiState.postValue(EditWordUiState.Normal)
    }
}
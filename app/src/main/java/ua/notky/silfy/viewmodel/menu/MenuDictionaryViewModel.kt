package ua.notky.silfy.viewmodel.menu

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.R
import ua.notky.silfy.models.model.DictionaryInfo
import ua.notky.silfy.models.states.DictionaryUiState
import ua.notky.silfy.usecase.dictionary.ClearAllBlacksUseCase
import ua.notky.silfy.usecase.dictionary.ClearAllFavouritesUseCase
import ua.notky.silfy.usecase.dictionary.ClearLearningProgressUseCase
import ua.notky.silfy.usecase.dictionary.DictionaryInfoUseCase
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 29.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
@HiltViewModel
class MenuDictionaryViewModel @Inject constructor(
    private val dictionaryInfoUseCase: DictionaryInfoUseCase,
    private val clearAllBlacksUseCase: ClearAllBlacksUseCase,
    private val clearAllFavouritesUseCase: ClearAllFavouritesUseCase,
    private val clearLearningProgressUseCase: ClearLearningProgressUseCase
) : BaseViewModel() {

    private val _dictionaryInfo: MutableLiveData<DictionaryInfo> = MutableLiveData()
    val dictionaryInfo: LiveData<DictionaryInfo> = _dictionaryInfo

    private val _uiState: MutableLiveData<DictionaryUiState> = MutableLiveData()
    val uiState: LiveData<DictionaryUiState> = _uiState

    fun fetchData() {
        viewModelScope.launch {
            _uiState.postValue(DictionaryUiState.Updating)

            val result = dictionaryInfoUseCase.fetch()

            val info = if (result.isSuccess) {
                result.getOrNull() ?: DictionaryInfo()
            } else {
                DictionaryInfo()
            }

            _dictionaryInfo.postValue(info)
            _uiState.postValue(DictionaryUiState.Updated)
        }
    }

    fun onCleanAllProgress(context: Context) {
        viewModelScope.launch {
            _uiState.postValue(DictionaryUiState.Updating)
            if (clearLearningProgressUseCase.clear().isSuccess) {
                fetchData()
                Toast.makeText(
                    context,
                    context.getText(R.string.text_clean_success),
                    Toast.LENGTH_SHORT
                ).show()
            }
            fetchData()
        }
    }

    fun onCleanFavourites() {
        viewModelScope.launch {
            _uiState.postValue(DictionaryUiState.Updating)
            clearAllFavouritesUseCase.clear()
            fetchData()
        }
    }

    fun onCleanBlacks() {
        viewModelScope.launch {
            _uiState.postValue(DictionaryUiState.Updating)
            clearAllBlacksUseCase.clear()
            fetchData()
        }
    }
}
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
import ua.notky.silfy.usecase.dictionary.DictionaryInfoUseCase
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 29.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
@HiltViewModel
class MenuDictionaryViewModel @Inject constructor(
    private val dictionaryInfoUseCase: DictionaryInfoUseCase
) : BaseViewModel() {

    private val _dictionaryInfo: MutableLiveData<DictionaryInfo> = MutableLiveData()
    val dictionaryInfo: LiveData<DictionaryInfo> = _dictionaryInfo

    fun fetchData() {
        viewModelScope.launch {
            val result = dictionaryInfoUseCase.fetch()

            val info = if (result.isSuccess) {
                result.getOrNull() ?: DictionaryInfo()
            } else {
                DictionaryInfo()
            }

            _dictionaryInfo.postValue(info)
        }
    }

    fun onCleanAllProgress(context: Context) {
        Toast.makeText(context, context.getText(R.string.text_clean_success), Toast.LENGTH_SHORT)
            .show()
    }

    fun onCleanFavourites() {
        _dictionaryInfo.postValue(
            _dictionaryInfo.value?.copy(
                favouriteWords = 0
            )
        )
    }

    fun onCleanBlacks() {
        _dictionaryInfo.postValue(
            _dictionaryInfo.value?.copy(
                blackWords = 0
            )
        )
    }
}
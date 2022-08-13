package ua.notky.silfy.viewmodel.go

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.models.model.Word
import ua.notky.silfy.usecase.go.UpdateWordMarkUseCase
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 13.08.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@HiltViewModel
class GoUpdateViewModel @Inject constructor(
    private val updateWordMarkUseCase: UpdateWordMarkUseCase
) : BaseViewModel() {

    fun updateFavouriteWord(id: Int?, checked: Boolean, words: MutableList<Word>) {
        viewModelScope.launch {
            val params = UpdateWordMarkUseCase.Params(id, checked, words)
            updateWordMarkUseCase.updateFavourite(params)
        }
    }

    fun updateBlackWord(id: Int?, checked: Boolean, words: MutableList<Word>) {
        viewModelScope.launch {
            val params = UpdateWordMarkUseCase.Params(id, checked, words)
            updateWordMarkUseCase.updateBlack(params)
        }
    }
}
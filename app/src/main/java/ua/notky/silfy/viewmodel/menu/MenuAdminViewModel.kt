package ua.notky.silfy.viewmodel.menu

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.content.usecase.FetchRawCategoriesUseCase
import ua.notky.content.usecase.FetchRawWordsUseCase
import ua.notky.silfy.models.observable.AdminStateModel
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 05.08.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@HiltViewModel
class MenuAdminViewModel @Inject constructor(
    private val fetchRawCategoriesUseCase: FetchRawCategoriesUseCase,
    private val fetchRawWordsUseCase: FetchRawWordsUseCase
) : BaseViewModel() {

    val state = AdminStateModel()

    fun fetchCategories() {
        viewModelScope.launch {
            state.categoriesLoading.set(true)
            state.categoriesMessage.set("is loading")
            fetchRawCategoriesUseCase.fetch {
                if (it.isSuccess) {
                    state.categoriesMessage.set("Loading is success!")
                } else {
                    state.categoriesMessage.set("Some problems: [${it.exceptionOrNull()?.message ?: ""}]")
                }
            }
            state.categoriesLoading.set(false)
        }
    }

    fun fetchWords() {
        viewModelScope.launch {
            state.wordsLoading.set(true)
            state.wordsMessage.set("is loading")
            fetchRawWordsUseCase.fetch {
                if (it.isSuccess) {
                    state.wordsMessage.set("Loading is success!")
                } else {
                    state.wordsMessage.set("Some problems: [${it.exceptionOrNull()?.message ?: ""}]")
                }
            }
            state.wordsLoading.set(false)
        }
    }
}
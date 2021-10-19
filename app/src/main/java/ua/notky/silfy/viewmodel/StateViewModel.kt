package ua.notky.silfy.viewmodel

import androidx.lifecycle.ViewModel
import ua.notky.silfy.model.enums.SortLang
import ua.notky.silfy.model.enums.SortState
import ua.notky.silfy.model.enums.SortType
import ua.notky.silfy.model.state.StateModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

class StateViewModel : ViewModel() {
    val state: StateModel = StateModel()

    fun setDefaultSort() {
        state.sortLang.set(SortLang.EN_DOWN)
        state.sortType.set(SortType.DISABLE)
        state.sortState.set(SortState.DISABLE)
    }

    fun setEnSort(){
        when(state.sortLang.get()) {
            SortLang.EN_UP -> state.sortLang.set(SortLang.EN_DOWN)
            SortLang.EN_DOWN -> state.sortLang.set(SortLang.EN_UP)
            else -> state.sortLang.set(SortLang.EN_DOWN)
        }
    }

    fun setRuSort() {
        when(state.sortLang.get()) {
            SortLang.RU_UP -> state.sortLang.set(SortLang.RU_DOWN)
            SortLang.RU_DOWN -> state.sortLang.set(SortLang.RU_UP)
            else -> state.sortLang.set(SortLang.RU_DOWN)
        }
    }

    fun setTypeSort() {
        when(state.sortType.get()) {
            SortType.FAVOURITE -> state.sortType.set(SortType.BLACKLIST)
            SortType.BLACKLIST -> state.sortType.set(SortType.DISABLE)
            SortType.DISABLE -> state.sortType.set(SortType.FAVOURITE)
        }
    }

    fun setStateSort() {
        when(state.sortState.get()) {
            SortState.EXCELLENT -> state.sortState.set(SortState.UNKNOWN)
            SortState.UNKNOWN -> state.sortState.set(SortState.DISABLE)
            SortState.DISABLE -> state.sortState.set(SortState.EXCELLENT)
        }
    }
}
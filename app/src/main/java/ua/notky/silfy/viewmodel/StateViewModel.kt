package ua.notky.silfy.viewmodel

import ua.notky.base.viewmodel.state.BaseStateViewModel
import ua.notky.silfy.models.observable.StateModel
import ua.notky.silfy.models.states.EditableState
import ua.notky.silfy.models.states.SortLang
import ua.notky.silfy.models.states.SortState
import ua.notky.silfy.models.states.SortType

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

class StateViewModel : BaseStateViewModel<StateModel>() {
    override val state: StateModel = StateModel()

    fun setDefaultSort() {
        state.sortLang.set(SortLang.EN_DOWN)
        state.sortType.set(SortType.DISABLE)
        state.sortState.set(SortState.DISABLE)
    }

    fun setEnSort() {
        when (state.sortLang.get()) {
            SortLang.EN_UP -> state.sortLang.set(SortLang.EN_DOWN)
            SortLang.EN_DOWN -> state.sortLang.set(SortLang.EN_UP)
            else -> state.sortLang.set(SortLang.EN_DOWN)
        }
    }

    fun setRuSort() {
        when (state.sortLang.get()) {
            SortLang.UA_UP -> state.sortLang.set(SortLang.UA_DOWN)
            SortLang.UA_DOWN -> state.sortLang.set(SortLang.UA_UP)
            else -> state.sortLang.set(SortLang.UA_DOWN)
        }
    }

    fun setTypeSort() {
        when (state.sortType.get()) {
            SortType.FAVOURITE -> state.sortType.set(SortType.BLACKLIST)
            SortType.BLACKLIST -> state.sortType.set(SortType.DISABLE)
            else -> state.sortType.set(SortType.FAVOURITE)
        }
    }

    fun setStateSort() {
        when (state.sortState.get()) {
            SortState.EXCELLENT -> state.sortState.set(SortState.UNKNOWN)
            SortState.UNKNOWN -> state.sortState.set(SortState.DISABLE)
            else -> state.sortState.set(SortState.EXCELLENT)
        }
    }

    fun updateEditable(isNew: Boolean) {
        if (isNew) {
            state.editableState.set(EditableState.NEW)
        } else {
            state.editableState.set(EditableState.EDIT)
        }
    }

    fun updatePresentValue(value: Boolean = false) {
        state.isPresentValue.set(value)
    }
}
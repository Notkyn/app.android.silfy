package ua.notky.silfy.viewmodel

import androidx.lifecycle.ViewModel
import ua.notky.silfy.models.states.SortLang
import ua.notky.silfy.models.states.SortState
import ua.notky.silfy.models.states.SortType
import ua.notky.silfy.models.observable.StateModel
import ua.notky.silfy.models.states.EditableState

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

class StateViewModel : ViewModel() {
    val stateModel: StateModel = StateModel()

    fun setDefaultSort() {
        stateModel.sortLang.set(SortLang.EN_DOWN)
        stateModel.sortType.set(SortType.DISABLE)
        stateModel.sortState.set(SortState.DISABLE)
    }

    fun setEnSort(){
        when(stateModel.sortLang.get()) {
            SortLang.EN_UP -> stateModel.sortLang.set(SortLang.EN_DOWN)
            SortLang.EN_DOWN -> stateModel.sortLang.set(SortLang.EN_UP)
            else -> stateModel.sortLang.set(SortLang.EN_DOWN)
        }
    }

    fun setRuSort() {
        when(stateModel.sortLang.get()) {
            SortLang.UA_UP -> stateModel.sortLang.set(SortLang.UA_DOWN)
            SortLang.UA_DOWN -> stateModel.sortLang.set(SortLang.UA_UP)
            else -> stateModel.sortLang.set(SortLang.UA_DOWN)
        }
    }

    fun setTypeSort() {
        when(stateModel.sortType.get()) {
            SortType.FAVOURITE -> stateModel.sortType.set(SortType.BLACKLIST)
            SortType.BLACKLIST -> stateModel.sortType.set(SortType.DISABLE)
            SortType.DISABLE -> stateModel.sortType.set(SortType.FAVOURITE)
        }
    }

    fun setStateSort() {
        when(stateModel.sortState.get()) {
            SortState.EXCELLENT -> stateModel.sortState.set(SortState.UNKNOWN)
            SortState.UNKNOWN -> stateModel.sortState.set(SortState.DISABLE)
            SortState.DISABLE -> stateModel.sortState.set(SortState.EXCELLENT)
        }
    }

    fun updateEditable(isNew: Boolean) {
        if(isNew) {
            stateModel.editableState.set(EditableState.NEW)
        } else {
            stateModel.editableState.set(EditableState.EDIT)
        }
    }
}
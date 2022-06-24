package ua.notky.silfy.models.observable

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import ua.notky.silfy.models.states.EditableState
import ua.notky.silfy.models.states.SortLang
import ua.notky.silfy.models.states.SortState
import ua.notky.silfy.models.states.SortType

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

data class StateModel(
    val sortLang: ObservableField<SortLang> = ObservableField(SortLang.EN_DOWN),
    val sortType: ObservableField<SortType> = ObservableField(SortType.DISABLE),
    val sortState: ObservableField<SortState> = ObservableField(SortState.DISABLE),
    val editableState: ObservableField<EditableState> = ObservableField(EditableState.NEW),
    val isPresentValue: ObservableBoolean = ObservableBoolean(false),
    val isLoading: ObservableBoolean = ObservableBoolean(false)
)
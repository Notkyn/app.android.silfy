package ua.notky.silfy.models.state

import androidx.databinding.ObservableField
import ua.notky.silfy.models.enums.SortLang
import ua.notky.silfy.models.enums.SortState
import ua.notky.silfy.models.enums.SortType

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */
data class StateModel(
    val sortLang: ObservableField<SortLang> = ObservableField(SortLang.EN_DOWN),
    val sortType: ObservableField<SortType> = ObservableField(SortType.DISABLE),
    val sortState: ObservableField<SortState> = ObservableField(SortState.DISABLE)
)
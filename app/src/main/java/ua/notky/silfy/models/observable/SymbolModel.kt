package ua.notky.silfy.models.observable

import androidx.databinding.ObservableBoolean

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 10.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

data class SymbolModel(
    val symbol: String,
    val isSelect: ObservableBoolean = ObservableBoolean(false)
)
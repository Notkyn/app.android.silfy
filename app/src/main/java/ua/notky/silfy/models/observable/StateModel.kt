package ua.notky.silfy.models.observable

import androidx.databinding.ObservableBoolean

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

data class StateModel(
    val isPresentValue: ObservableBoolean = ObservableBoolean(false),
    val isLoading: ObservableBoolean = ObservableBoolean(false),
    val isPreviousInfo: ObservableBoolean = ObservableBoolean(false),
    val isNextInfo: ObservableBoolean = ObservableBoolean(false),
    val isAdmin: ObservableBoolean = ObservableBoolean(false),
)
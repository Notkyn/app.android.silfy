package ua.notky.silfy.models.observable

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 05.08.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
data class AdminStateModel(
    val categoriesLoading: ObservableBoolean = ObservableBoolean(false),
    val categoriesMessage: ObservableField<String> = ObservableField("")
)

package ua.notky.silfy.models.observable

import androidx.databinding.ObservableField

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 18.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

data class FormWordModel(
    val value: ObservableField<String> = ObservableField("")
)
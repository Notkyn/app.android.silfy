package ua.notky.silfy.models.observable

import androidx.databinding.ObservableField

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

data class MenuModel(
    val version: ObservableField<String> = ObservableField("")
)
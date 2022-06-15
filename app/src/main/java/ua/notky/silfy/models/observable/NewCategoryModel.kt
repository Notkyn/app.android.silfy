package ua.notky.silfy.models.observable

import androidx.databinding.ObservableField

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 15.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

data class NewCategoryModel(
    val name: ObservableField<String> = ObservableField("")
)
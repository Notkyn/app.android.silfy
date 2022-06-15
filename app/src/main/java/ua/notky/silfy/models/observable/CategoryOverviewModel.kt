package ua.notky.silfy.models.observable

import androidx.databinding.ObservableField
import ua.notky.silfy.models.model.Word

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 15.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
data class CategoryOverviewModel(
    var id: Int? = null,
    val title: ObservableField<String> = ObservableField(""),
    val words: ObservableField<List<Word>> = ObservableField(listOf())
)

package ua.notky.silfy.models.observable

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import ua.notky.silfy.models.states.WordState

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

data class WordsModel(
    var id: Int? = null,
    val state: ObservableField<WordState> = ObservableField(WordState.UNKNOWN),
    val isFavourite: ObservableBoolean = ObservableBoolean(false),
    val isBlacklist: ObservableBoolean = ObservableBoolean(false),
    val isChanged: ObservableBoolean = ObservableBoolean(false)
)
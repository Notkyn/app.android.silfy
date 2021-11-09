package ua.notky.silfy.models.model

import ua.notky.silfy.models.states.WordState

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 21.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

data class Word(
    val id: Int? = null,
    val en: String = "",
    val ua: String = "",
    val state: WordState = WordState.UNKNOWN,
    val isFavourite: Boolean = false,
    val isBlacklist: Boolean = false,
    val categories: MutableList<Category> = mutableListOf()
)
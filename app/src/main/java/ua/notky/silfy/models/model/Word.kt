package ua.notky.silfy.models.model

import ua.notky.silfy.models.enums.GoLangType
import ua.notky.silfy.models.states.WordState
import kotlin.random.Random

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
) {
    private fun getMoreTranslate(): List<String> {
        return ua.lowercase().split(",")
    }

    fun getOneTranslate(): String {
        val list = getMoreTranslate()
        return list[Random.nextInt(list.size)]
    }

    fun checkByType(value: String, type: GoLangType): Boolean {
        return when(type) {
            GoLangType.EN -> value.lowercase() == this.en.lowercase()
            GoLangType.UA -> this.getMoreTranslate().contains(value.lowercase())
        }
    }
}
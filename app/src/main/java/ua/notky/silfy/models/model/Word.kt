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
    val minCountState: Int = WordState.UNKNOWN.minCount,
    val isFavourite: Boolean = false,
    val isBlacklist: Boolean = false
) {
    private fun getMoreTranslate(): List<String> {
        return ua.lowercase().split(",").map { it.trim() }
    }

    fun getValueByType(type: GoLangType): String {
        return when (type) {
            GoLangType.EN -> en.lowercase().trim()
            GoLangType.UA -> getOneTranslate().lowercase().trim()
        }
    }

    private fun getOneTranslate(): String {
        val list = getMoreTranslate()
        return list[Random.nextInt(list.size)]
    }

    fun checkByType(value: String?, type: GoLangType?): Boolean {
        if (value.isNullOrEmpty() || type == null) return false
        return when (type) {
            GoLangType.EN -> value.lowercase().trim() == this.en.lowercase().trim()
            GoLangType.UA -> this.getMoreTranslate().contains(value.lowercase().trim())
        }
    }

    fun isNew() = id == null
}
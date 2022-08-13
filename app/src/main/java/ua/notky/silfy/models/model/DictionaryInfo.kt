package ua.notky.silfy.models.model

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 29.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

data class DictionaryInfo(
    val allWords: Int = 0,
    val byStateStats: List<DictionaryByStateInfo> = listOf(),
    val favouriteWords: Int = 0,
    val blackWords: Int = 0
)
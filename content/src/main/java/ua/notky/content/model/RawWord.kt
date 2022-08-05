package ua.notky.content.model

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 05.08.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

data class RawWord(
    val en: String,
    val ua: String,
    val categories: List<String>
)
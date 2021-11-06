package ua.notky.silfy.models.model

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

data class Category(
    val id: Int,
    val title: String,
    val words: List<Word>
)

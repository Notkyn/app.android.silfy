package ua.notky.silfy.models.enums

/**
 * @project Silfy
 * @author Evgeniy Zarechnyi on 06.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
enum class SelectedWordsType(val id: Int, val type: String) {
    ALL(1, "all"),
    FAVOURITE(2, "favourite")
}
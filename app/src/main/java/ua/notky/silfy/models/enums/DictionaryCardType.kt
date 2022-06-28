package ua.notky.silfy.models.enums

import ua.notky.silfy.R

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 29.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
enum class DictionaryCardType(
    val title: Int,
    val color: Int,
    val action: Int
) {
    ALL(
        title = R.string.text_count_all_words,
        color = R.color.bg_card_dictionary_all_words,
        action = R.string.button_clear_learning_progress
    ),
    FAVOURITE(
        title = R.string.text_count_favourites_words,
        color = R.color.bg_card_dictionary_favourites_words,
        action = R.string.button_clear_list_words
    ),
    BLACK(
        title = R.string.text_count_black_words,
        color = R.color.bg_card_dictionary_black_words,
        action = R.string.button_clear_list_words
    )
}
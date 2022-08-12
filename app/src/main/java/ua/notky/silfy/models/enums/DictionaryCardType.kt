package ua.notky.silfy.models.enums

import ua.notky.silfy.R

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 29.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
enum class DictionaryCardType(
    val title: Int,
    val action: Int,
    val alertTitle: Int
) {
    ALL(
        title = R.string.text_count_all_words,
        action = R.string.button_clear_learning_progress,
        alertTitle = R.string.alert_title_clean_all_progress_dictionary
    ),
    FAVOURITE(
        title = R.string.text_count_favourites_words,
        action = R.string.button_clear_list_words,
        alertTitle = R.string.alert_title_clean_favourite_dictionary
    ),
    BLACK(
        title = R.string.text_count_black_words,
        action = R.string.button_clear_list_words,
        alertTitle = R.string.alert_title_clean_black_dictionary
    )
}
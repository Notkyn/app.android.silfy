package ua.notky.silfy.models.enums

import ua.notky.silfy.R

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 11.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
enum class StatsPointType(
    val title: Int,
    val color: Int
) {
    TOTAL_WORDS(R.string.text_stats_total_words, R.color.secondary_color),
    USED_WORDS(R.string.text_stats_used_words, R.color.blue_light),
    SUCCESS(R.string.text_stats_success, R.color.button_answer_success),
    ERRORS(R.string.text_stats_errors, R.color.button_answer_error)
}
package ua.notky.silfy.models.enums

import ua.notky.silfy.R

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 11.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
enum class GoStatsType(val id: Int, val title: Int) {
    TIME(1, R.string.text_go_stats_time),
    ERROR(2, R.string.text_go_stats_errors),
    OTHER(3, R.string.text_go_stats_result),
    NONE(4, R.string.text_empty)
}
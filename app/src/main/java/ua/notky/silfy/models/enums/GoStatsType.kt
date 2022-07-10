package ua.notky.silfy.models.enums

import ua.notky.silfy.R

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 11.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
enum class GoStatsType(val title: Int) {
    TIME(R.string.text_go_stats_time),
    ERROR(R.string.text_go_stats_errors),
    OTHER(R.string.text_go_stats_result)
}
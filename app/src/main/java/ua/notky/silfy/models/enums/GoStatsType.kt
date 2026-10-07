package ua.notky.silfy.models.enums

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 11.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

/** Why a training session ended; [id] is stored in `session_stats`. Texts — SessionResultsFragment */
enum class GoStatsType(val id: Int) {
    TIME(1),
    ERROR(2),
    OTHER(3),
    NONE(4)
}

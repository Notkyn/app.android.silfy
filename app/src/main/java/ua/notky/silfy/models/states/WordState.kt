package ua.notky.silfy.models.states

import ua.notky.silfy.R

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 21.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

enum class WordState(
    val id: Int,
    val value: String,
    val image: Int,
    val minCount: Int
) {
    EXCELLENT(
        1,
        "excellent",
        R.drawable.ic_word_state_excellent,
        100
    ),
    GOOD(
        2,
        "good",
        R.drawable.ic_word_state_good,
        80
    ),
    AVERAGE(
        3,
        "average",
        R.drawable.ic_word_state_average,
        60
    ),
    POOR(
        4,
        "poor",
        R.drawable.ic_word_state_poor,
        30
    ),
    UNKNOWN(
        5,
        "unknown",
        R.drawable.ic_word_state_unknown,
        0
    );

    companion object {

        fun getStateById(id: Int): WordState {
            return when (id) {
                EXCELLENT.id -> EXCELLENT
                GOOD.id -> GOOD
                AVERAGE.id -> AVERAGE
                POOR.id -> POOR
                else -> UNKNOWN
            }
        }

        fun getByCount(count: Int): WordState {
            return when {
                count >= POOR.minCount && count < AVERAGE.minCount -> POOR
                count >= AVERAGE.minCount && count < GOOD.minCount -> AVERAGE
                count >= GOOD.minCount && count < EXCELLENT.minCount -> GOOD
                count >= EXCELLENT.minCount -> EXCELLENT
                else -> UNKNOWN
            }
        }
    }
}
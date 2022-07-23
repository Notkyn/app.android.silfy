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
    val title: Int
) {
    EXCELLENT(1, "excellent", R.drawable.ic_word_state_excellent, R.string.text_word_state_excellent),
    GOOD(2, "good", R.drawable.ic_word_state_good, R.string.text_word_state_good),
    AVERAGE(3, "average", R.drawable.ic_word_state_average, R.string.text_word_state_average),
    POOR(4, "poor", R.drawable.ic_word_state_poor, R.string.text_word_state_poor),
    UNKNOWN(5, "unknown", R.drawable.ic_word_state_unknown, R.string.text_word_state_unknown);

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
    }
}
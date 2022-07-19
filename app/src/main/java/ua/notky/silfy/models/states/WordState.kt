package ua.notky.silfy.models.states

import ua.notky.silfy.R

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 21.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

enum class WordState(
    name: String,
    val image: Int,
    val title: Int
) {
    EXCELLENT("excellent", R.drawable.ic_word_state_excellent, R.string.text_word_state_excellent),
    GOOD("good", R.drawable.ic_word_state_good, R.string.text_word_state_good),
    AVERAGE("average", R.drawable.ic_word_state_average, R.string.text_word_state_average),
    POOR("poor", R.drawable.ic_word_state_poor, R.string.text_word_state_poor),
    UNKNOWN("unknown", R.drawable.ic_word_state_unknown, R.string.text_word_state_unknown);

    companion object {
        const val DEFAULT = "unknown"
    }
}
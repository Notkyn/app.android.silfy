package ua.notky.silfy.models.states

import ua.notky.silfy.R

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 21.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

enum class WordState(val image: Int, val title: Int) {
    EXCELLENT(R.drawable.ic_word_state_excellent, R.string.text_word_state_excellent),
    GOOD(R.drawable.ic_word_state_good, R.string.text_word_state_good),
    AVERAGE(R.drawable.ic_word_state_average, R.string.text_word_state_average),
    POOR(R.drawable.ic_word_state_poor, R.string.text_word_state_poor),
    UNKNOWN(R.drawable.ic_word_state_unknown, R.string.text_word_state_unknown)
}
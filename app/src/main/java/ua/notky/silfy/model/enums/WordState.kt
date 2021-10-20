package ua.notky.silfy.model.enums

import ua.notky.silfy.R

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 21.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */
enum class WordState(val image: Int) {
    EXCELLENT(R.drawable.ic_word_state_excellent),
    GOOD(R.drawable.ic_word_state_good),
    AVERAGE(R.drawable.ic_word_state_good),
    POOR(R.drawable.ic_word_state_poor),
    UNKNOWN(R.drawable.ic_word_state_unknown)
}
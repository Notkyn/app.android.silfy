package ua.notky.silfy.ui.view.level

import androidx.annotation.ColorRes
import ua.notky.silfy.R
import ua.notky.silfy.models.states.WordState

/**
 * Maps the point-based [WordState] to the 5 knowledge levels of the design:
 * Unknown 0 · Poor 1 · Average 2 · Good 3 · Excellent 4.
 */
const val MAX_WORD_LEVEL = 4

val WordState.level: Int
    get() = when (this) {
        WordState.UNKNOWN -> 0
        WordState.POOR -> 1
        WordState.AVERAGE -> 2
        WordState.GOOD -> 3
        WordState.EXCELLENT -> 4
    }

@get:ColorRes
val WordState.levelColor: Int
    get() = levelColor(level)

@ColorRes
fun levelColor(level: Int): Int {
    return when (level) {
        1 -> R.color.level_poor
        2 -> R.color.level_average
        3 -> R.color.level_good
        4 -> R.color.level_excellent
        else -> R.color.level_unknown
    }
}

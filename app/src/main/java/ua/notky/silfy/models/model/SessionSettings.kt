package ua.notky.silfy.models.model

import ua.notky.silfy.models.enums.DifficultType

/**
 * 4a / 6b Training settings of the profile. Stored in the `settings` table (+ `settings_category_cross`),
 * see SessionSettingsUseCase for how the 1.x values (free mistake count, endless duration) are read.
 */
data class SessionSettings(
    val difficulty: DifficultType = DifficultType.EASY,
    /** One of [DURATIONS] */
    val minutes: Int = DURATIONS.first(),
    val isMistakeLimit: Boolean = false,
    /** One of [MISTAKE_LIMITS]; kept while the limit is off */
    val maxMistakes: Int = MISTAKE_LIMITS[1],
    val isFavouritesOnly: Boolean = false,
    val isBlacklistIncluded: Boolean = false,
    /** Empty — all words */
    val categoryIds: Set<Int> = emptySet()
) {
    val isHard: Boolean get() = difficulty == DifficultType.HARD

    companion object {
        val DURATIONS = listOf(5, 10, 30)
        val MISTAKE_LIMITS = listOf(3, 5, 10)

        /** The closest allowed value: 1.x let the user type any number */
        fun closest(values: List<Int>, value: Int): Int = values.minByOrNull { kotlin.math.abs(it - value) } ?: values.first()
    }
}

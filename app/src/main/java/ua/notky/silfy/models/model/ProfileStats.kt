package ua.notky.silfy.models.model

import ua.notky.silfy.models.states.WordState

/** 5a Progress: words of the profile by knowledge level + the Favourites / Blacklist tiles */
data class ProfileStats(
    val total: Int = 0,
    val favourites: Int = 0,
    val blacklist: Int = 0,
    val excellent: Int = 0,
    val good: Int = 0,
    val average: Int = 0,
    val poor: Int = 0,
    val unknown: Int = 0
) {
    /** Excellent → Unknown, the order of the distribution bar and its legend */
    val levels: List<Pair<WordState, Int>>
        get() = listOf(
            WordState.EXCELLENT to excellent,
            WordState.GOOD to good,
            WordState.AVERAGE to average,
            WordState.POOR to poor,
            WordState.UNKNOWN to unknown
        )
}

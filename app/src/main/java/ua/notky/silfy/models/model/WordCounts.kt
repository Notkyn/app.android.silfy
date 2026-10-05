package ua.notky.silfy.models.model

/** Counters on the dictionary tabs: All / Favourites / Blacklist */
data class WordCounts(
    val total: Int = 0,
    val favourites: Int = 0,
    val blacklist: Int = 0
)

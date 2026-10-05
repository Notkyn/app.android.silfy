package ua.notky.silfy.models.enums

import androidx.annotation.StringRes
import ua.notky.silfy.R

/**
 * Dictionary sort button cycles A–Z → Z–A → Level.
 * Level: Unknown first, words of one level — A–Z. [ordinal] is also the sort code in WordListDao.
 */
enum class WordSortMode(@StringRes val label: Int) {
    A_Z(R.string.sort_a_z),
    Z_A(R.string.sort_z_a),
    LEVEL(R.string.dictionary_sort_level);

    fun next(): WordSortMode = values()[(ordinal + 1) % values().size]
}

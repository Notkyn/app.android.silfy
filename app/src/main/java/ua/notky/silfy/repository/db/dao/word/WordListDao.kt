package ua.notky.silfy.repository.db.dao.word

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import ua.notky.silfy.models.enums.DictionaryTab
import ua.notky.silfy.models.enums.WordSortMode
import ua.notky.silfy.models.local.WordLocal
import ua.notky.silfy.models.model.WordCounts

/** Word lists: the dictionary (2a/2b) — one query for every tab, search and sort mode; a category (3b) */
@Dao
interface WordListDao {

    /**
     * @param tab [DictionaryTab.ordinal]: 0 — all, 1 — favourites, 2 — blacklist
     * @param sort [WordSortMode.ordinal]: 0 — A–Z, 1 — Z–A, 2 — level (`state` 5 = Unknown … 1 = Excellent), then A–Z
     */
    @Query(
        "SELECT * FROM word WHERE user_id = :userId " +
                "AND (:tab = 0 OR (:tab = 1 AND favourite = 1) OR (:tab = 2 AND black = 1)) " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY CASE WHEN :sort = 2 THEN state END DESC, " +
                "CASE WHEN :sort = 1 THEN en END COLLATE NOCASE DESC, " +
                "en COLLATE NOCASE ASC"
    )
    fun getWords(userId: Int, tab: Int, search: String, sort: Int): LiveData<List<WordLocal>>

    @Query(
        "SELECT COUNT(*) AS total, " +
                "COALESCE(SUM(favourite), 0) AS favourites, " +
                "COALESCE(SUM(black), 0) AS blacklist " +
                "FROM word WHERE user_id = :userId"
    )
    fun getCounts(userId: Int): LiveData<WordCounts>

    /** 3b Category: words of the category, A–Z */
    @Query(
        "SELECT w.* FROM word w INNER JOIN word_category_cross x ON x.word_id = w.word_id " +
                "WHERE x.category_id = :categoryId AND w.user_id = :userId " +
                "ORDER BY w.en COLLATE NOCASE ASC"
    )
    fun getCategoryWords(userId: Int, categoryId: Int): LiveData<List<WordLocal>>

    /**
     * Training pool (4a / session): favourites only or all words, with or without the blacklist,
     * words of any of [categoryIds] or of all categories when [anyCategory].
     */
    @Query(
        "SELECT * FROM word w WHERE w.user_id = :userId " +
                "AND (:favouritesOnly = 0 OR w.favourite = 1) " +
                "AND (:withBlacklist = 1 OR w.black = 0) " +
                "AND (:anyCategory = 1 OR EXISTS (SELECT 1 FROM word_category_cross x " +
                "WHERE x.word_id = w.word_id AND x.category_id IN (:categoryIds)))"
    )
    suspend fun getSessionWords(
        userId: Int,
        favouritesOnly: Boolean,
        withBlacklist: Boolean,
        anyCategory: Boolean,
        categoryIds: List<Int>
    ): List<WordLocal>

    /** Same filter as [getSessionWords]: "{n} words match these settings" */
    @Query(
        "SELECT COUNT(*) FROM word w WHERE w.user_id = :userId " +
                "AND (:favouritesOnly = 0 OR w.favourite = 1) " +
                "AND (:withBlacklist = 1 OR w.black = 0) " +
                "AND (:anyCategory = 1 OR EXISTS (SELECT 1 FROM word_category_cross x " +
                "WHERE x.word_id = w.word_id AND x.category_id IN (:categoryIds)))"
    )
    fun countSessionWords(
        userId: Int,
        favouritesOnly: Boolean,
        withBlacklist: Boolean,
        anyCategory: Boolean,
        categoryIds: List<Int>
    ): LiveData<Int>
}

package ua.notky.silfy.repository.db.dao.word

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import ua.notky.silfy.models.enums.DictionaryTab
import ua.notky.silfy.models.enums.WordSortMode
import ua.notky.silfy.models.local.WordLocal
import ua.notky.silfy.models.model.WordCounts

/** Dictionary list (2a/2b): one query for every tab, search and sort mode */
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
}

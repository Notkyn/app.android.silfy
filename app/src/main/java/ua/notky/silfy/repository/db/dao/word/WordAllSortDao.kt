package ua.notky.silfy.repository.db.dao.word

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import ua.notky.silfy.models.local.WordLocal

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 20.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
@Dao
interface WordAllSortDao {
    @Query(
        "SELECT * FROM word WHERE user_id = :userID " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY en DESC"
    )
    fun getAllBySortEnUp(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY en"
    )
    fun getAllBySortEnDown(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY favourite DESC, en DESC"
    )
    fun getAllBySortEnUpFavourite(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY black DESC, en DESC"
    )
    fun getAllBySortEnUpBlack(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY favourite DESC, en"
    )
    fun getAllBySortEnDownFavourite(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY black DESC, en"
    )
    fun getAllBySortEnDownBlack(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY state, en DESC"
    )
    fun getAllBySortEnUpStateUp(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY state DESC, en DESC"
    )
    fun getAllBySortEnUpStateDown(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY state, en"
    )
    fun getAllBySortEnDownStateUp(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY state DESC, en"
    )
    fun getAllBySortEnDownStateDown(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY favourite DESC, state, en DESC"
    )
    fun getAllBySortEnUpStateUpFavourite(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY black DESC, state, en DESC"
    )
    fun getAllBySortEnUpStateUpBlack(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY favourite DESC, state DESC, en DESC"
    )
    fun getAllBySortEnUpStateDownFavourite(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY black DESC, state DESC, en DESC"
    )
    fun getAllBySortEnUpStateDownBlack(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY favourite DESC, state, en"
    )
    fun getAllBySortEnDownStateUpFavourite(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY black DESC, state, en"
    )
    fun getAllBySortEnDownStateUpBlack(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY favourite DESC, state DESC, en"
    )
    fun getAllBySortEnDownStateDownFavourite(
        userID: Int?,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY black DESC, state DESC, en"
    )
    fun getAllBySortEnDownStateDownBlack(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY translation DESC"
    )
    fun getAllBySortTranslationUp(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY translation"
    )
    fun getAllBySortTranslationDown(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY favourite DESC, translation DESC"
    )
    fun getAllBySortTranslationUpFavourite(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY black DESC, translation DESC"
    )
    fun getAllBySortTranslationUpBlack(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY favourite DESC, translation"
    )
    fun getAllBySortTranslationDownFavourite(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY black DESC, translation"
    )
    fun getAllBySortTranslationDownBlack(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY state, translation DESC"
    )
    fun getAllBySortTranslationUpStateUp(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY state DESC, translation DESC"
    )
    fun getAllBySortTranslationUpStateDown(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY state, translation"
    )
    fun getAllBySortTranslationDownStateUp(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY state DESC, translation"
    )
    fun getAllBySortTranslationDownStateDown(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY favourite DESC, state, translation DESC"
    )
    fun getAllBySortTranslationUpStateUpFavourite(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY black DESC, state, translation DESC"
    )
    fun getAllBySortTranslationUpStateUpBlack(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY favourite DESC, state DESC, translation DESC"
    )
    fun getAllBySortTranslationUpStateDownFavourite(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY black DESC, state DESC, translation DESC"
    )
    fun getAllBySortTranslationUpStateDownBlack(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY favourite DESC, state, translation"
    )
    fun getAllBySortTranslationDownStateUpFavourite(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY black DESC, state, translation"
    )
    fun getAllBySortTranslationDownStateUpBlack(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY favourite DESC, state DESC, translation"
    )
    fun getAllBySortTranslationDownStateDownFavourite(
        userID: Int?,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY black DESC, state DESC, translation"
    )
    fun getAllBySortTranslationDownStateDownBlack(userID: Int?, search: String): LiveData<List<WordLocal>>
}
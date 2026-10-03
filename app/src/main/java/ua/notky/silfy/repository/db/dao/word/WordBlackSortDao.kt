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
interface WordBlackSortDao {

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND black = :isBlack " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY en DESC"
    )
    fun getAllBySortEnUp(userID: Int?, isBlack: Boolean, search: String): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND black = :isBlack " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY en"
    )
    fun getAllBySortEnDown(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND black = :isBlack " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY favourite DESC, en DESC"
    )
    fun getAllBySortEnUpFavourite(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND black = :isBlack " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY black DESC, en DESC"
    )
    fun getAllBySortEnUpBlack(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND black = :isBlack " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY favourite DESC, en"
    )
    fun getAllBySortEnDownFavourite(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND black = :isBlack " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY black DESC, en"
    )
    fun getAllBySortEnDownBlack(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND black = :isBlack " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY state ASC, en DESC"
    )
    fun getAllBySortEnUpStateUp(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND black = :isBlack " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY state DESC, en DESC"
    )
    fun getAllBySortEnUpStateDown(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND black = :isBlack " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY state ASC, en"
    )
    fun getAllBySortEnDownStateUp(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND black = :isBlack " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY state DESC, en"
    )
    fun getAllBySortEnDownStateDown(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND black = :isBlack " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY favourite DESC, state ASC, en DESC"
    )
    fun getAllBySortEnUpStateUpFavourite(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND black = :isBlack " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY black DESC, state ASC, en DESC"
    )
    fun getAllBySortEnUpStateUpBlack(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND black = :isBlack " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY favourite DESC, state DESC, en DESC"
    )
    fun getAllBySortEnUpStateDownFavourite(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND black = :isBlack " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY black DESC, state DESC, en DESC"
    )
    fun getAllBySortEnUpStateDownBlack(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND black = :isBlack " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY favourite DESC, state ASC, en"
    )
    fun getAllBySortEnDownStateUpFavourite(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND black = :isBlack " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY black DESC, state ASC, en"
    )
    fun getAllBySortEnDownStateUpBlack(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND black = :isBlack " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY favourite DESC, state DESC, en"
    )
    fun getAllBySortEnDownStateDownFavourite(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND black = :isBlack " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY black DESC, state DESC, en"
    )
    fun getAllBySortEnDownStateDownBlack(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND black = :isBlack " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY translation DESC"
    )
    fun getAllBySortTranslationUp(userID: Int?, isBlack: Boolean, search: String): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND black = :isBlack " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY translation"
    )
    fun getAllBySortTranslationDown(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND black = :isBlack " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY favourite DESC, translation DESC"
    )
    fun getAllBySortTranslationUpFavourite(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND black = :isBlack " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY black DESC, translation DESC"
    )
    fun getAllBySortTranslationUpBlack(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND black = :isBlack " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY favourite DESC, translation"
    )
    fun getAllBySortTranslationDownFavourite(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND black = :isBlack " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY black DESC, translation"
    )
    fun getAllBySortTranslationDownBlack(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND black = :isBlack " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY state ASC, translation DESC"
    )
    fun getAllBySortTranslationUpStateUp(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND black = :isBlack " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY state DESC, translation DESC"
    )
    fun getAllBySortTranslationUpStateDown(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND black = :isBlack " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY state ASC, translation DESC"
    )
    fun getAllBySortTranslationDownStateUp(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND black = :isBlack " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY state DESC, translation"
    )
    fun getAllBySortTranslationDownStateDown(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND black = :isBlack " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY favourite DESC, state ASC, translation DESC"
    )
    fun getAllBySortTranslationUpStateUpFavourite(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND black = :isBlack " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY black DESC, state ASC, translation DESC"
    )
    fun getAllBySortTranslationUpStateUpBlack(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND black = :isBlack " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY favourite DESC, state DESC, translation DESC"
    )
    fun getAllBySortTranslationUpStateDownFavourite(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND black = :isBlack " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY black DESC, state DESC, translation DESC"
    )
    fun getAllBySortTranslationUpStateDownBlack(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND black = :isBlack " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY favourite DESC, state ASC, translation"
    )
    fun getAllBySortTranslationDownStateUpFavourite(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND black = :isBlack " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY black DESC, state ASC, translation"
    )
    fun getAllBySortTranslationDownStateUpBlack(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND black = :isBlack " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY favourite DESC, state DESC, translation"
    )
    fun getAllBySortTranslationDownStateDownFavourite(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND black = :isBlack " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY black DESC, state DESC, translation"
    )
    fun getAllBySortTranslationDownStateDownBlack(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>
}
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
interface WordFavouriteSortDao {

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND favourite = :isFavourite " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY en DESC"
    )
    fun getAllBySortEnUp(
        userID: Int?,
        isFavourite: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND favourite = :isFavourite " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY en"
    )
    fun getAllBySortEnDown(
        userID: Int?,
        isFavourite: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND favourite = :isFavourite " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY favourite DESC, en DESC"
    )
    fun getAllBySortEnUpFavourite(
        userID: Int?,
        isFavourite: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND favourite = :isFavourite " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY black DESC, en DESC"
    )
    fun getAllBySortEnUpBlack(
        userID: Int?,
        isFavourite: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND favourite = :isFavourite " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY favourite DESC, en"
    )
    fun getAllBySortEnDownFavourite(
        userID: Int?,
        isFavourite: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND favourite = :isFavourite " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY black DESC, en"
    )
    fun getAllBySortEnDownBlack(
        userID: Int?,
        isFavourite: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND favourite = :isFavourite " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY state ASC, en DESC"
    )
    fun getAllBySortEnUpStateUp(
        userID: Int?,
        isFavourite: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND favourite = :isFavourite " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY state DESC, en DESC"
    )
    fun getAllBySortEnUpStateDown(
        userID: Int?,
        isFavourite: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND favourite = :isFavourite " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY state ASC, en"
    )
    fun getAllBySortEnDownStateUp(
        userID: Int?,
        isFavourite: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND favourite = :isFavourite " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY state DESC, en"
    )
    fun getAllBySortEnDownStateDown(
        userID: Int?,
        isFavourite: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND favourite = :isFavourite " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY favourite DESC, state ASC, en DESC"
    )
    fun getAllBySortEnUpStateUpFavourite(
        userID: Int?,
        isFavourite: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND favourite = :isFavourite " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY black DESC, state ASC, en DESC"
    )
    fun getAllBySortEnUpStateUpBlack(
        userID: Int?,
        isFavourite: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND favourite = :isFavourite " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY favourite DESC, state DESC, en DESC"
    )
    fun getAllBySortEnUpStateDownFavourite(
        userID: Int?,
        isFavourite: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND favourite = :isFavourite " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY black DESC, state DESC, en DESC"
    )
    fun getAllBySortEnUpStateDownBlack(
        userID: Int?,
        isFavourite: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND favourite = :isFavourite " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY favourite DESC, state ASC, en"
    )
    fun getAllBySortEnDownStateUpFavourite(
        userID: Int?,
        isFavourite: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND favourite = :isFavourite " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY black DESC, state ASC, en"
    )
    fun getAllBySortEnDownStateUpBlack(
        userID: Int?,
        isFavourite: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND favourite = :isFavourite " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY favourite DESC, state DESC, en"
    )
    fun getAllBySortEnDownStateDownFavourite(
        userID: Int?,
        isFavourite: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND favourite = :isFavourite " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY black DESC, state DESC, en"
    )
    fun getAllBySortEnDownStateDownBlack(
        userID: Int?,
        isFavourite: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND favourite = :isFavourite " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY translation DESC"
    )
    fun getAllBySortTranslationUp(
        userID: Int?,
        isFavourite: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND favourite = :isFavourite " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY translation"
    )
    fun getAllBySortTranslationDown(
        userID: Int?,
        isFavourite: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND favourite = :isFavourite " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY favourite DESC, translation DESC"
    )
    fun getAllBySortTranslationUpFavourite(
        userID: Int?,
        isFavourite: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND favourite = :isFavourite " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY black DESC, translation DESC"
    )
    fun getAllBySortTranslationUpBlack(
        userID: Int?,
        isFavourite: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND favourite = :isFavourite " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY favourite DESC, translation"
    )
    fun getAllBySortTranslationDownFavourite(
        userID: Int?,
        isFavourite: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND favourite = :isFavourite " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY black DESC, translation"
    )
    fun getAllBySortTranslationDownBlack(
        userID: Int?,
        isFavourite: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND favourite = :isFavourite " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY state ASC, translation DESC"
    )
    fun getAllBySortTranslationUpStateUp(
        userID: Int?,
        isFavourite: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND favourite = :isFavourite " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY state DESC, translation DESC"
    )
    fun getAllBySortTranslationUpStateDown(
        userID: Int?,
        isFavourite: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND favourite = :isFavourite " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY state ASC, translation"
    )
    fun getAllBySortTranslationDownStateUp(
        userID: Int?,
        isFavourite: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND favourite = :isFavourite " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY state DESC, translation"
    )
    fun getAllBySortTranslationDownStateDown(
        userID: Int?,
        isFavourite: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND favourite = :isFavourite " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY favourite DESC, state ASC, translation DESC"
    )
    fun getAllBySortTranslationUpStateUpFavourite(
        userID: Int?,
        isFavourite: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND favourite = :isFavourite " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY black DESC, state ASC, translation DESC"
    )
    fun getAllBySortTranslationUpStateUpBlack(
        userID: Int?,
        isFavourite: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND favourite = :isFavourite " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY favourite DESC, state DESC, translation DESC"
    )
    fun getAllBySortTranslationUpStateDownFavourite(
        userID: Int?,
        isFavourite: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND favourite = :isFavourite " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY black DESC, state DESC, translation DESC"
    )
    fun getAllBySortTranslationUpStateDownBlack(
        userID: Int?,
        isFavourite: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND favourite = :isFavourite " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY favourite DESC, state ASC, translation"
    )
    fun getAllBySortTranslationDownStateUpFavourite(
        userID: Int?,
        isFavourite: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND favourite = :isFavourite " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY black DESC, state ASC, translation"
    )
    fun getAllBySortTranslationDownStateUpBlack(
        userID: Int?,
        isFavourite: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND favourite = :isFavourite " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY favourite DESC, state DESC, translation"
    )
    fun getAllBySortTranslationDownStateDownFavourite(
        userID: Int?,
        isFavourite: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query(
        "SELECT * FROM word WHERE user_id = :userID AND favourite = :isFavourite " +
                "AND (en LIKE '%' || :search || '%' OR translation LIKE '%' || :search || '%') " +
                "ORDER BY black DESC, state DESC, translation"
    )
    fun getAllBySortTranslationDownStateDownBlack(
        userID: Int?,
        isFavourite: Boolean,
        search: String
    ): LiveData<List<WordLocal>>
}
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

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY en DESC")
    fun getAllBySortEnUp(userID: Int?, isBlack: Boolean, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY en")
    fun getAllBySortEnDown(userID: Int?, isBlack: Boolean, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, en DESC")
    fun getAllBySortEnUpFavourite(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, en DESC")
    fun getAllBySortEnUpBlack(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, en")
    fun getAllBySortEnDownFavourite(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, en")
    fun getAllBySortEnDownBlack(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state ASC, en DESC")
    fun getAllBySortEnUpStateUp(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state DESC, en DESC")
    fun getAllBySortEnUpStateDown(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state ASC, en")
    fun getAllBySortEnDownStateUp(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state DESC, en")
    fun getAllBySortEnDownStateDown(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state ASC, en DESC")
    fun getAllBySortEnUpStateUpFavourite(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state ASC, en DESC")
    fun getAllBySortEnUpStateUpBlack(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state DESC, en DESC")
    fun getAllBySortEnUpStateDownFavourite(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state DESC, en DESC")
    fun getAllBySortEnUpStateDownBlack(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state ASC, en")
    fun getAllBySortEnDownStateUpFavourite(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state ASC, en")
    fun getAllBySortEnDownStateUpBlack(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state DESC, en")
    fun getAllBySortEnDownStateDownFavourite(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state DESC, en")
    fun getAllBySortEnDownStateDownBlack(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY ua DESC")
    fun getAllBySortUaUp(userID: Int?, isBlack: Boolean, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY ua")
    fun getAllBySortUaDown(userID: Int?, isBlack: Boolean, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, ua DESC")
    fun getAllBySortUaUpFavourite(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, ua DESC")
    fun getAllBySortUaUpBlack(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, ua")
    fun getAllBySortUaDownFavourite(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, ua")
    fun getAllBySortUaDownBlack(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state ASC, ua DESC")
    fun getAllBySortUaUpStateUp(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state DESC, ua DESC")
    fun getAllBySortUaUpStateDown(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state ASC, ua DESC")
    fun getAllBySortUaDownStateUp(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state DESC, ua")
    fun getAllBySortUaDownStateDown(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state ASC, ua DESC")
    fun getAllBySortUaUpStateUpFavourite(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state ASC, ua DESC")
    fun getAllBySortUaUpStateUpBlack(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state DESC, ua DESC")
    fun getAllBySortUaUpStateDownFavourite(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state DESC, ua DESC")
    fun getAllBySortUaUpStateDownBlack(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state ASC, ua")
    fun getAllBySortUaDownStateUpFavourite(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state ASC, ua")
    fun getAllBySortUaDownStateUpBlack(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state DESC, ua")
    fun getAllBySortUaDownStateDownFavourite(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state DESC, ua")
    fun getAllBySortUaDownStateDownBlack(
        userID: Int?,
        isBlack: Boolean,
        search: String
    ): LiveData<List<WordLocal>>
}
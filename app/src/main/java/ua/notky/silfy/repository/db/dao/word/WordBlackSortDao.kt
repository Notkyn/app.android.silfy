package ua.notky.silfy.repository.db.dao.word

import androidx.room.Dao
import androidx.room.Query
import ua.notky.silfy.models.local.WordData

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 20.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
@Dao
interface WordBlackSortDao {

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY en DESC")
    suspend fun getAllBySortEnUp(userID: Int?, isBlack: Boolean, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY en")
    suspend fun getAllBySortEnDown(userID: Int?, isBlack: Boolean, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, en DESC")
    suspend fun getAllBySortEnUpFavourite(userID: Int?, isBlack: Boolean, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, en DESC")
    suspend fun getAllBySortEnUpBlack(userID: Int?, isBlack: Boolean, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, en")
    suspend fun getAllBySortEnDownFavourite(userID: Int?, isBlack: Boolean, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, en")
    suspend fun getAllBySortEnDownBlack(userID: Int?, isBlack: Boolean, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state ASC, en DESC")
    suspend fun getAllBySortEnUpStateUp(userID: Int?, isBlack: Boolean, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state DESC, en DESC")
    suspend fun getAllBySortEnUpStateDown(userID: Int?, isBlack: Boolean, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state ASC, en")
    suspend fun getAllBySortEnDownStateUp(userID: Int?, isBlack: Boolean, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state DESC, en")
    suspend fun getAllBySortEnDownStateDown(userID: Int?, isBlack: Boolean, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state ASC, en DESC")
    suspend fun getAllBySortEnUpStateUpFavourite(userID: Int?, isBlack: Boolean, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state ASC, en DESC")
    suspend fun getAllBySortEnUpStateUpBlack(userID: Int?, isBlack: Boolean, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state DESC, en DESC")
    suspend fun getAllBySortEnUpStateDownFavourite(userID: Int?, isBlack: Boolean, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state DESC, en DESC")
    suspend fun getAllBySortEnUpStateDownBlack(userID: Int?, isBlack: Boolean, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state ASC, en")
    suspend fun getAllBySortEnDownStateUpFavourite(userID: Int?, isBlack: Boolean, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state ASC, en")
    suspend fun getAllBySortEnDownStateUpBlack(userID: Int?, isBlack: Boolean, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state DESC, en")
    suspend fun getAllBySortEnDownStateDownFavourite(userID: Int?, isBlack: Boolean, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state DESC, en")
    suspend fun getAllBySortEnDownStateDownBlack(userID: Int?, isBlack: Boolean, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY ua DESC")
    suspend fun getAllBySortUaUp(userID: Int?, isBlack: Boolean, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY ua")
    suspend fun getAllBySortUaDown(userID: Int?, isBlack: Boolean, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, ua DESC")
    suspend fun getAllBySortUaUpFavourite(userID: Int?, isBlack: Boolean, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, ua DESC")
    suspend fun getAllBySortUaUpBlack(userID: Int?, isBlack: Boolean, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, ua")
    suspend fun getAllBySortUaDownFavourite(userID: Int?, isBlack: Boolean, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, ua")
    suspend fun getAllBySortUaDownBlack(userID: Int?, isBlack: Boolean, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state ASC, ua DESC")
    suspend fun getAllBySortUaUpStateUp(userID: Int?, isBlack: Boolean, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state DESC, ua DESC")
    suspend fun getAllBySortUaUpStateDown(userID: Int?, isBlack: Boolean, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state ASC, ua DESC")
    suspend fun getAllBySortUaDownStateUp(userID: Int?, isBlack: Boolean, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state DESC, ua")
    suspend fun getAllBySortUaDownStateDown(userID: Int?, isBlack: Boolean, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state ASC, ua DESC")
    suspend fun getAllBySortUaUpStateUpFavourite(userID: Int?, isBlack: Boolean, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state ASC, ua DESC")
    suspend fun getAllBySortUaUpStateUpBlack(userID: Int?, isBlack: Boolean, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state DESC, ua DESC")
    suspend fun getAllBySortUaUpStateDownFavourite(userID: Int?, isBlack: Boolean, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state DESC, ua DESC")
    suspend fun getAllBySortUaUpStateDownBlack(userID: Int?, isBlack: Boolean, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state ASC, ua")
    suspend fun getAllBySortUaDownStateUpFavourite(userID: Int?, isBlack: Boolean, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state ASC, ua")
    suspend fun getAllBySortUaDownStateUpBlack(userID: Int?, isBlack: Boolean, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state DESC, ua")
    suspend fun getAllBySortUaDownStateDownFavourite(userID: Int?, isBlack: Boolean, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND black = :isBlack AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state DESC, ua")
    suspend fun getAllBySortUaDownStateDownBlack(userID: Int?, isBlack: Boolean, search: String): List<WordData>
}
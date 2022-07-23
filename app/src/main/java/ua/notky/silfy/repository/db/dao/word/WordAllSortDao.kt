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
interface WordAllSortDao {
    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY en DESC")
    suspend fun getAllBySortEnUp(userID: Int?, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY en")
    suspend fun getAllBySortEnDown(userID: Int?, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, en DESC")
    suspend fun getAllBySortEnUpFavourite(userID: Int?, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, en DESC")
    suspend fun getAllBySortEnUpBlack(userID: Int?, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, en")
    suspend fun getAllBySortEnDownFavourite(userID: Int?, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, en")
    suspend fun getAllBySortEnDownBlack(userID: Int?, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state, en DESC")
    suspend fun getAllBySortEnUpStateUp(userID: Int?, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state DESC, en DESC")
    suspend fun getAllBySortEnUpStateDown(userID: Int?, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state, en")
    suspend fun getAllBySortEnDownStateUp(userID: Int?, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state DESC, en")
    suspend fun getAllBySortEnDownStateDown(userID: Int?, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state, en DESC")
    suspend fun getAllBySortEnUpStateUpFavourite(userID: Int?, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state, en DESC")
    suspend fun getAllBySortEnUpStateUpBlack(userID: Int?, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state DESC, en DESC")
    suspend fun getAllBySortEnUpStateDownFavourite(userID: Int?, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state DESC, en DESC")
    suspend fun getAllBySortEnUpStateDownBlack(userID: Int?, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state, en")
    suspend fun getAllBySortEnDownStateUpFavourite(userID: Int?, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state, en")
    suspend fun getAllBySortEnDownStateUpBlack(userID: Int?, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state DESC, en")
    suspend fun getAllBySortEnDownStateDownFavourite(userID: Int?, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state DESC, en")
    suspend fun getAllBySortEnDownStateDownBlack(userID: Int?, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY ua DESC")
    suspend fun getAllBySortUaUp(userID: Int?, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY ua")
    suspend fun getAllBySortUaDown(userID: Int?, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, ua DESC")
    suspend fun getAllBySortUaUpFavourite(userID: Int?, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, ua DESC")
    suspend fun getAllBySortUaUpBlack(userID: Int?, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, ua")
    suspend fun getAllBySortUaDownFavourite(userID: Int?, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, ua")
    suspend fun getAllBySortUaDownBlack(userID: Int?, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state, ua DESC")
    suspend fun getAllBySortUaUpStateUp(userID: Int?, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state DESC, ua DESC")
    suspend fun getAllBySortUaUpStateDown(userID: Int?, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state, ua")
    suspend fun getAllBySortUaDownStateUp(userID: Int?, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state DESC, ua")
    suspend fun getAllBySortUaDownStateDown(userID: Int?, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state, ua DESC")
    suspend fun getAllBySortUaUpStateUpFavourite(userID: Int?, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state, ua DESC")
    suspend fun getAllBySortUaUpStateUpBlack(userID: Int?, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state DESC, ua DESC")
    suspend fun getAllBySortUaUpStateDownFavourite(userID: Int?, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state DESC, ua DESC")
    suspend fun getAllBySortUaUpStateDownBlack(userID: Int?, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state, ua")
    suspend fun getAllBySortUaDownStateUpFavourite(userID: Int?, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state, ua")
    suspend fun getAllBySortUaDownStateUpBlack(userID: Int?, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state DESC, ua")
    suspend fun getAllBySortUaDownStateDownFavourite(userID: Int?, search: String): List<WordData>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state DESC, ua")
    suspend fun getAllBySortUaDownStateDownBlack(userID: Int?, search: String): List<WordData>
}
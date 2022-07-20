package ua.notky.silfy.repository.db.dao

import androidx.room.Dao
import androidx.room.Query
import ua.notky.silfy.models.local.WordDb

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 20.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
@Dao
interface WordAllSortDao {
    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY en ASC")
    suspend fun getAllBySortEnUp(userID: Int?, search: String): List<WordDb>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY en DESC")
    suspend fun getAllBySortEnDown(userID: Int?, search: String): List<WordDb>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, en ASC")
    suspend fun getAllBySortEnUpFavourite(userID: Int?, search: String): List<WordDb>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, en DESC")
    suspend fun getAllBySortEnUpBlack(userID: Int?, search: String): List<WordDb>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, en ASC")
    suspend fun getAllBySortEnDownFavourite(userID: Int?, search: String): List<WordDb>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, en DESC")
    suspend fun getAllBySortEnDownBlack(userID: Int?, search: String): List<WordDb>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state ASC, en ASC")
    suspend fun getAllBySortEnUpStateUp(userID: Int?, search: String): List<WordDb>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state DESC, en DESC")
    suspend fun getAllBySortEnUpStateDown(userID: Int?, search: String): List<WordDb>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state ASC, en ASC")
    suspend fun getAllBySortEnDownStateUp(userID: Int?, search: String): List<WordDb>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state DESC, en DESC")
    suspend fun getAllBySortEnDownStateDown(userID: Int?, search: String): List<WordDb>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state ASC, en DESC")
    suspend fun getAllBySortEnUpStateUpFavourite(userID: Int?, search: String): List<WordDb>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state ASC, en DESC")
    suspend fun getAllBySortEnUpStateUpBlack(userID: Int?, search: String): List<WordDb>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state DESC, en DESC")
    suspend fun getAllBySortEnUpStateDownFavourite(userID: Int?, search: String): List<WordDb>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state DESC, en DESC")
    suspend fun getAllBySortEnUpStateDownBlack(userID: Int?, search: String): List<WordDb>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state ASC, en DESC")
    suspend fun getAllBySortEnDownStateUpFavourite(userID: Int?, search: String): List<WordDb>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state ASC, en DESC")
    suspend fun getAllBySortEnDownStateUpBlack(userID: Int?, search: String): List<WordDb>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state DESC, en DESC")
    suspend fun getAllBySortEnDownStateDownFavourite(userID: Int?, search: String): List<WordDb>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state DESC, en DESC")
    suspend fun getAllBySortEnDownStateDownBlack(userID: Int?, search: String): List<WordDb>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY en ASC")
    suspend fun getAllBySortUaUp(userID: Int?, search: String): List<WordDb>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY en DESC")
    suspend fun getAllBySortUaDown(userID: Int?, search: String): List<WordDb>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, ua ASC")
    suspend fun getAllBySortUaUpFavourite(userID: Int?, search: String): List<WordDb>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, ua DESC")
    suspend fun getAllBySortUaUpBlack(userID: Int?, search: String): List<WordDb>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, ua ASC")
    suspend fun getAllBySortUaDownFavourite(userID: Int?, search: String): List<WordDb>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, ua DESC")
    suspend fun getAllBySortUaDownBlack(userID: Int?, search: String): List<WordDb>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state ASC, ua ASC")
    suspend fun getAllBySortUaUpStateUp(userID: Int?, search: String): List<WordDb>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state DESC, ua DESC")
    suspend fun getAllBySortUaUpStateDown(userID: Int?, search: String): List<WordDb>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state ASC, ua ASC")
    suspend fun getAllBySortUaDownStateUp(userID: Int?, search: String): List<WordDb>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state DESC, ua DESC")
    suspend fun getAllBySortUaDownStateDown(userID: Int?, search: String): List<WordDb>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state ASC, ua DESC")
    suspend fun getAllBySortUaUpStateUpFavourite(userID: Int?, search: String): List<WordDb>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state ASC, ua DESC")
    suspend fun getAllBySortUaUpStateUpBlack(userID: Int?, search: String): List<WordDb>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state DESC, ua DESC")
    suspend fun getAllBySortUaUpStateDownFavourite(userID: Int?, search: String): List<WordDb>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state DESC, ua DESC")
    suspend fun getAllBySortUaUpStateDownBlack(userID: Int?, search: String): List<WordDb>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state ASC, ua DESC")
    suspend fun getAllBySortUaDownStateUpFavourite(userID: Int?, search: String): List<WordDb>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state ASC, ua DESC")
    suspend fun getAllBySortUaDownStateUpBlack(userID: Int?, search: String): List<WordDb>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state DESC, ua DESC")
    suspend fun getAllBySortUaDownStateDownFavourite(userID: Int?, search: String): List<WordDb>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state DESC, ua DESC")
    suspend fun getAllBySortUaDownStateDownBlack(userID: Int?, search: String): List<WordDb>
}
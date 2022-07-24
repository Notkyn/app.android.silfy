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
    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY en DESC")
    fun getAllBySortEnUp(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY en")
    fun getAllBySortEnDown(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, en DESC")
    fun getAllBySortEnUpFavourite(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, en DESC")
    fun getAllBySortEnUpBlack(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, en")
    fun getAllBySortEnDownFavourite(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, en")
    fun getAllBySortEnDownBlack(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state, en DESC")
    fun getAllBySortEnUpStateUp(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state DESC, en DESC")
    fun getAllBySortEnUpStateDown(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state, en")
    fun getAllBySortEnDownStateUp(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state DESC, en")
    fun getAllBySortEnDownStateDown(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state, en DESC")
    fun getAllBySortEnUpStateUpFavourite(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state, en DESC")
    fun getAllBySortEnUpStateUpBlack(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state DESC, en DESC")
    fun getAllBySortEnUpStateDownFavourite(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state DESC, en DESC")
    fun getAllBySortEnUpStateDownBlack(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state, en")
    fun getAllBySortEnDownStateUpFavourite(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state, en")
    fun getAllBySortEnDownStateUpBlack(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state DESC, en")
    fun getAllBySortEnDownStateDownFavourite(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state DESC, en")
    fun getAllBySortEnDownStateDownBlack(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY ua DESC")
    fun getAllBySortUaUp(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY ua")
    fun getAllBySortUaDown(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, ua DESC")
    fun getAllBySortUaUpFavourite(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, ua DESC")
    fun getAllBySortUaUpBlack(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, ua")
    fun getAllBySortUaDownFavourite(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, ua")
    fun getAllBySortUaDownBlack(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state, ua DESC")
    fun getAllBySortUaUpStateUp(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state DESC, ua DESC")
    fun getAllBySortUaUpStateDown(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state, ua")
    fun getAllBySortUaDownStateUp(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY state DESC, ua")
    fun getAllBySortUaDownStateDown(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state, ua DESC")
    fun getAllBySortUaUpStateUpFavourite(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state, ua DESC")
    fun getAllBySortUaUpStateUpBlack(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state DESC, ua DESC")
    fun getAllBySortUaUpStateDownFavourite(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state DESC, ua DESC")
    fun getAllBySortUaUpStateDownBlack(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state, ua")
    fun getAllBySortUaDownStateUpFavourite(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state, ua")
    fun getAllBySortUaDownStateUpBlack(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY favourite DESC, state DESC, ua")
    fun getAllBySortUaDownStateDownFavourite(userID: Int?, search: String): LiveData<List<WordLocal>>

    @Query("SELECT * FROM word WHERE user_id = :userID AND (en LIKE '%' || :search || '%' OR ua LIKE '%' || :search || '%') ORDER BY black DESC, state DESC, ua")
    fun getAllBySortUaDownStateDownBlack(userID: Int?, search: String): LiveData<List<WordLocal>>
}
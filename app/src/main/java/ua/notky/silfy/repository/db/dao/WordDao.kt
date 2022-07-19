package ua.notky.silfy.repository.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ua.notky.silfy.models.local.WordDb

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(words: List<WordDb>)

    @Query("DELETE FROM word WHERE user_id = :userID")
    suspend fun clearAll(userID: Int)

    @Query("SELECT * FROM word WHERE user_id = :userID")
    suspend fun getAll(userID: Int): List<WordDb>
}
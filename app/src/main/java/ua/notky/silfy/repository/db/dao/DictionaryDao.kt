package ua.notky.silfy.repository.db.dao

import androidx.room.Dao
import androidx.room.Query

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@Dao
interface DictionaryDao {
    @Query("SELECT count(*) FROM word WHERE user_id = :userId")
    suspend fun getCountAll(userId: Int): Int

    @Query("SELECT count(*) FROM word WHERE user_id = :userId AND favourite = :isFavourite")
    suspend fun getCountFavourites(userId: Int, isFavourite: Boolean = true): Int

    @Query("SELECT count(*) FROM word WHERE user_id = :userId AND black = :isBlack")
    suspend fun getCountBlacks(userId: Int, isBlack: Boolean = true): Int

    @Query("SELECT count(*) FROM word WHERE user_id = :userId AND state = :state")
    suspend fun getCountByState(userId: Int, state: Int): Int
}
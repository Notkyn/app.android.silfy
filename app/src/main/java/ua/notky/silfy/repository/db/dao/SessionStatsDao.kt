package ua.notky.silfy.repository.db.dao

import androidx.room.*
import ua.notky.silfy.models.local.SessionStatsLocal

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 25.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@Dao
interface SessionStatsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stats: SessionStatsLocal)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(stats: SessionStatsLocal)

    @Query("SELECT * FROM session_stats WHERE id = :id AND user_id = :userId")
    suspend fun get(id: String, userId: Int): SessionStatsLocal?
}
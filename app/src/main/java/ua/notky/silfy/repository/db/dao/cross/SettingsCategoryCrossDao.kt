package ua.notky.silfy.repository.db.dao.cross

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ua.notky.silfy.models.local.cross.SettingsCategoryCrossRef

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@Dao
interface SettingsCategoryCrossDao {

    @Query("DELETE FROM settings_category_cross WHERE user_id = :userId")
    suspend fun clearAll(userId: Int)

    @Query("DELETE FROM settings_category_cross WHERE category_id = :categoryId AND user_id = :userId")
    suspend fun deleteByCategory(categoryId: Int, userId: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(cross: List<SettingsCategoryCrossRef>)
}
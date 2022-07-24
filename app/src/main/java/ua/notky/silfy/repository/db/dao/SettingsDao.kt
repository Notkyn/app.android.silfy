package ua.notky.silfy.repository.db.dao

import androidx.room.*
import ua.notky.silfy.models.local.SettingsLocal
import ua.notky.silfy.models.local.cross.SettingsWithCategory

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@Dao
interface SettingsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(settings: SettingsLocal)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(settings: SettingsLocal)

    @Query("DELETE FROM settings WHERE user_id = :userId")
    suspend fun remove(userId: Int)

    @Transaction
    suspend fun replace(userId: Int, settings: SettingsLocal) {
        remove(userId)
        insert(settings)
    }

    @Transaction
    @Query("SELECT * FROM settings WHERE user_id = :userId")
    suspend fun getWithCategories(userId: Int): SettingsWithCategory?
}
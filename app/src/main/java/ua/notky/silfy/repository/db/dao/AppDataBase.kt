package ua.notky.silfy.repository.db.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import ua.notky.silfy.models.model.Profile
import ua.notky.silfy.repository.db.dao.AppDataBase.Companion.DATABASE_VERSION

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@Database(
    entities = [Profile::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun profileDao(): ProfileDao

    companion object {
        const val DATABASE_NAME = "silfy_database"
        const val DATABASE_VERSION = 1
    }
}
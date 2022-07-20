package ua.notky.silfy.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ua.notky.silfy.models.local.WordDb
import ua.notky.silfy.models.model.Profile
import ua.notky.silfy.repository.db.AppDataBase.Companion.DATABASE_VERSION
import ua.notky.silfy.repository.db.dao.*

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@Database(
    entities = [Profile::class, WordDb::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun profileDao(): ProfileDao
    abstract fun wordDao(): WordDao
    abstract fun wordAllSortDao() : WordAllSortDao
    abstract fun wordFavouriteSortDao(): WordFavouriteSortDao
    abstract fun wordBlackSortDao(): WordBlackSortDao

    companion object {
        const val DATABASE_NAME = "silfy_database"
        const val DATABASE_VERSION = 1
    }
}
package ua.notky.silfy.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ua.notky.silfy.models.local.CategoryLocal
import ua.notky.silfy.models.local.WordLocal
import ua.notky.silfy.models.local.cross.WordCategoryCrossRef
import ua.notky.silfy.models.model.Profile
import ua.notky.silfy.repository.db.AppDataBase.Companion.DATABASE_VERSION
import ua.notky.silfy.repository.db.dao.CategoryDao
import ua.notky.silfy.repository.db.dao.DictionaryDao
import ua.notky.silfy.repository.db.dao.ProfileDao
import ua.notky.silfy.repository.db.dao.WordCategoryCrossDao
import ua.notky.silfy.repository.db.dao.word.WordAllSortDao
import ua.notky.silfy.repository.db.dao.word.WordBlackSortDao
import ua.notky.silfy.repository.db.dao.word.WordDao
import ua.notky.silfy.repository.db.dao.word.WordFavouriteSortDao

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@Database(
    entities = [
        Profile::class,
        WordLocal::class,
        CategoryLocal::class,
        WordCategoryCrossRef::class
    ],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun profileDao(): ProfileDao

    abstract fun wordDao(): WordDao
    abstract fun wordAllSortDao(): WordAllSortDao
    abstract fun wordFavouriteSortDao(): WordFavouriteSortDao
    abstract fun wordBlackSortDao(): WordBlackSortDao

    abstract fun categoryDao(): CategoryDao

    abstract fun dictionaryDao(): DictionaryDao

    abstract fun crossWordCategoryDao(): WordCategoryCrossDao

    companion object {
        const val DATABASE_NAME = "silfy_database"
        const val DATABASE_VERSION = 1
    }
}
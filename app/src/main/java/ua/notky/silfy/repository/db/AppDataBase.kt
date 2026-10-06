package ua.notky.silfy.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ua.notky.silfy.models.local.CategoryLocal
import ua.notky.silfy.models.local.SessionStatsLocal
import ua.notky.silfy.models.local.SettingsLocal
import ua.notky.silfy.models.local.WordLocal
import ua.notky.silfy.models.local.cross.SettingsCategoryCrossRef
import ua.notky.silfy.models.local.cross.WordCategoryCrossRef
import ua.notky.silfy.models.model.Profile
import ua.notky.silfy.repository.db.AppDataBase.Companion.DATABASE_VERSION
import ua.notky.silfy.repository.db.converter.AppLanguageConverter
import ua.notky.silfy.repository.db.converter.ListIntConverter
import ua.notky.silfy.repository.db.converter.MapResultConverter
import ua.notky.silfy.repository.db.dao.*
import ua.notky.silfy.repository.db.dao.cross.SettingsCategoryCrossDao
import ua.notky.silfy.repository.db.dao.cross.WordCategoryCrossDao
import ua.notky.silfy.repository.db.dao.word.WordDao
import ua.notky.silfy.repository.db.dao.word.WordListDao

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
        WordCategoryCrossRef::class,
        SettingsLocal::class,
        SettingsCategoryCrossRef::class,
        SessionStatsLocal::class
    ],
    version = DATABASE_VERSION,
    exportSchema = true
)
@TypeConverters(
    ListIntConverter::class,
    MapResultConverter::class,
    AppLanguageConverter::class
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun profileDao(): ProfileDao

    abstract fun wordDao(): WordDao
    abstract fun wordListDao(): WordListDao

    abstract fun categoryDao(): CategoryDao
    abstract fun settingsDao(): SettingsDao
    abstract fun sessionStatsDao(): SessionStatsDao

    abstract fun crossWordCategoryDao(): WordCategoryCrossDao
    abstract fun crossSettingsCategoryDao(): SettingsCategoryCrossDao

    companion object {
        const val DATABASE_NAME = "silfy_database"
        const val DATABASE_VERSION = 2
    }
}
package ua.notky.silfy.usecase

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import ua.notky.silfy.models.enums.DifficultType
import ua.notky.silfy.models.enums.SelectedWordsType
import ua.notky.silfy.models.enums.TrainingDurationType
import ua.notky.silfy.models.local.SettingsLocal
import ua.notky.silfy.repository.db.dao.CategoryDao
import ua.notky.silfy.repository.db.dao.ProfileDao
import ua.notky.silfy.repository.db.dao.SettingsDao
import ua.notky.silfy.repository.db.dao.cross.SettingsCategoryCrossDao
import ua.notky.silfy.repository.db.dao.cross.WordCategoryCrossDao
import ua.notky.silfy.repository.db.dao.word.WordDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import ua.notky.silfy.util.fetchAllCrossRefs
import ua.notky.silfy.util.fetchCategoryDtos
import ua.notky.silfy.util.getWordsFromAssets
import ua.notky.silfy.util.mapToLocal
import ua.notky.silfy.util.toCategoriesLocal
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class SaveDefaultDataUseCase @Inject constructor(
    @ApplicationContext val context: Context,
    private val dataStore: AppDataStorePreferences,
    private val profileDao: ProfileDao,
    private val wordDao: WordDao,
    private val categoryDao: CategoryDao,
    private val crossWordRefsDao: WordCategoryCrossDao,
    private val settingsDao: SettingsDao,
    private val crossSettingDao: SettingsCategoryCrossDao
) {

    suspend fun fetch(): Result<Unit> {
        return try {
            val userId = dataStore.getProfileId() ?: throw IllegalStateException("User is missing")

            val language = profileDao.getById(userId)?.language
                ?: throw IllegalStateException("Profile is missing")

            val wordDtos = context.getWordsFromAssets()
            val categoryDtos = context.fetchCategoryDtos()
            val words = wordDtos.mapToLocal(userId, language)
            val categories = categoryDtos.toCategoriesLocal(userId, language)

            wordDao.replaceAll(userId, words)
            categoryDao.replaceAll(userId, categories)

            val wordData = wordDao.getAll(userId)
            val categoryData = categoryDao.getAll(userId)
            val crossRefs = fetchAllCrossRefs(
                wordData, wordDtos, categoryDtos, categoryData, language, userId
            )

            crossWordRefsDao.replaceAll(userId, crossRefs)

            saveSettings(userId)

            Result.success(Unit)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    private suspend fun saveSettings(userId: Int) {
        val settings = SettingsLocal(
            userId,
            DifficultType.EASY.id,
            TrainingDurationType.FIVE.id,
            false,
            0,
            SelectedWordsType.ALL.id,
            false,
            userId
        )

        settingsDao.replace(userId, settings)
        crossSettingDao.clearAll(userId)
    }
}
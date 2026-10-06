package ua.notky.silfy.usecase.dictionary

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import ua.notky.silfy.repository.db.dao.CategoryDao
import ua.notky.silfy.repository.db.dao.ProfileDao
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
 * @author Yevgeniy Zarechniy on 13.08.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class ResetDefaultWordsUseCase @Inject constructor(
    @ApplicationContext val context: Context,
    private val dataStore: AppDataStorePreferences,
    private val profileDao: ProfileDao,
    private val wordDao: WordDao,
    private val categoryDao: CategoryDao,
    private val crossWordRefsDao: WordCategoryCrossDao
) {

    suspend fun reset(): Result<Unit> {
        return try {
            val userId = dataStore.getProfileId() ?: throw IllegalStateException("User is missing")

            val language = profileDao.getById(userId)?.language
                ?: throw IllegalStateException("Profile is missing")

            val wordDtos = context.getWordsFromAssets()
            val categoryDtos = context.fetchCategoryDtos()
            val words = wordDtos.mapToLocal(userId, language)
            val categories = categoryDtos.toCategoriesLocal(userId, language)

            crossWordRefsDao.clearAll(userId)
            // All categories are replaced, the ones the user created too (6d: "…categories … will be replaced")
            categoryDao.clearAll(userId)
            categoryDao.insertAll(categories)
            wordDao.replaceAll(userId, words)

            val wordData = wordDao.getAll(userId)
            val categoryData = categoryDao.getAll(userId)
            val crossRefs = fetchAllCrossRefs(
                wordData, wordDtos, categoryDtos, categoryData, language, userId
            )

            crossWordRefsDao.replaceAll(userId, crossRefs)

            Result.success(Unit)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }
}
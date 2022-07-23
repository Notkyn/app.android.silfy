package ua.notky.silfy.usecase.category

import ua.notky.silfy.mapper.category.CategoryMapper
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.repository.db.dao.CategoryDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 23.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class LoadAllCategoriesUseCase @Inject constructor(
    private val dataStore: AppDataStorePreferences,
    private val categoryDao: CategoryDao
) {

    suspend fun load(): Result<List<Category>> {
        return try {
            val userId = dataStore.getProfileId() ?: throw IllegalStateException("User is missing")
            val data = categoryDao.getCategoriesWithWords(userId)

            Result.success(CategoryMapper.map(data))
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }
}
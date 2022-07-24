package ua.notky.silfy.usecase.category

import ua.notky.silfy.repository.db.dao.CategoryDao
import ua.notky.silfy.repository.db.dao.WordCategoryCrossDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class DeleteCategoryUseCase @Inject constructor(
    private val dataStore: AppDataStorePreferences,
    private val categoryDao: CategoryDao,
    private val crossDao: WordCategoryCrossDao
) {
    suspend fun delete(params: Params): Result<Unit> {
        return try {
            val userId = dataStore.getProfileId() ?: throw IllegalStateException("User is missing")
            val categoryId = params.categoryId ?: throw IllegalStateException("Category Id is missing")

            crossDao.deleteByCategory(categoryId, userId)
            categoryDao.remove(categoryId, userId)

            Result.success(Unit)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    data class Params(
        val categoryId: Int?
    )
}
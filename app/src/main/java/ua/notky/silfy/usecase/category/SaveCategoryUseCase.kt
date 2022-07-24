package ua.notky.silfy.usecase.category

import ua.notky.silfy.models.local.CategoryLocal
import ua.notky.silfy.repository.db.dao.CategoryDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class SaveCategoryUseCase @Inject constructor(
    private val dataStore: AppDataStorePreferences,
    private val categoryDao: CategoryDao
) {
    suspend fun save(params: Params): Result<Unit> {
        return try {
            val userId = dataStore.getProfileId() ?: throw IllegalStateException("User is missing")
            val name = params.name ?: throw IllegalStateException("Category name is missing")

            if (params.id == null) {
                categoryDao.insert(CategoryLocal(title = name, userId = userId))
            } else {
                categoryDao.update(CategoryLocal(params.id, name, userId))
            }

            Result.success(Unit)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    data class Params(
        val id: Int?,
        val name: String?
    )
}
package ua.notky.silfy.usecase.category

import ua.notky.silfy.models.local.CategoryLocal
import ua.notky.silfy.repository.db.dao.CategoryDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import ua.notky.silfy.util.normalizeCategoryName
import javax.inject.Inject

class CategoryExistsException(name: String) : IllegalStateException("Category \"$name\" already exists")

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class SaveCategoryUseCase @Inject constructor(
    private val dataStore: AppDataStorePreferences,
    private val categoryDao: CategoryDao
) {
    /**
     * Saves the name with single spaces. Fails with [CategoryExistsException] when the profile
     * already has another category with this name (ignoring case, for any alphabet).
     */
    suspend fun save(params: Params): Result<Unit> {
        return try {
            val userId = dataStore.getProfileId() ?: throw IllegalStateException("User is missing")
            val name = normalizeCategoryName(params.name)

            val isTaken = categoryDao.getAll(userId).any {
                it.id != params.id && it.title.trim().equals(name, ignoreCase = true)
            }
            if (isTaken) throw CategoryExistsException(name)

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
        /** null — new category */
        val id: Int?,
        val name: String
    )
}

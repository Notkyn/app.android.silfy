package ua.notky.silfy.usecase.profile

import ua.notky.silfy.models.model.Profile
import ua.notky.silfy.repository.db.dao.CategoryDao
import ua.notky.silfy.repository.db.dao.ProfileDao
import ua.notky.silfy.repository.db.dao.SettingsDao
import ua.notky.silfy.repository.db.dao.cross.SettingsCategoryCrossDao
import ua.notky.silfy.repository.db.dao.cross.WordCategoryCrossDao
import ua.notky.silfy.repository.db.dao.word.WordDao
import ua.notky.silfy.tools.image.deleteByUriWithFileScheme
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class DeleteProfileUseCase @Inject constructor(
    private val profileDao: ProfileDao,
    private val wordDao: WordDao,
    private val categoryDao: CategoryDao,
    private val crossWordRefsDao: WordCategoryCrossDao,
    private val settingsDao: SettingsDao,
    private val crossSettingDao: SettingsCategoryCrossDao
) {

    suspend fun delete(params: Params): Result<Unit> {
        return try {
            val userId = params.profile.id ?: throw IllegalStateException("Profile Id is empty")

            params.profile.photo.deleteByUriWithFileScheme()

            profileDao.deleteById(userId)
            wordDao.clearAll(userId)
            categoryDao.clearAll(userId)
            crossWordRefsDao.clearAll(userId)
            settingsDao.remove(userId)
            crossSettingDao.clearAll(userId)

            Result.success(Unit)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    data class Params(
        val profile: Profile
    )
}
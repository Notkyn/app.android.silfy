package ua.notky.silfy.usecase.settings

import ua.notky.silfy.mapper.settings.SettingsMapper
import ua.notky.silfy.models.local.cross.SettingsCategoryCrossRef
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.models.model.TrainingSettings
import ua.notky.silfy.repository.db.dao.SettingsDao
import ua.notky.silfy.repository.db.dao.cross.SettingsCategoryCrossDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class UpdateSettingsUseCase @Inject constructor(
    private val dataStore: AppDataStorePreferences,
    private val settingsDao: SettingsDao,
    private val crossDao: SettingsCategoryCrossDao
) {

    suspend fun update(params: Params): Result<Unit> {
        return try {
            val userId = dataStore.getProfileId() ?: throw IllegalStateException("User is missing")

            val mapperParams = SettingsMapper.Params(userId)
            val updatedSettings = SettingsMapper.map(params.settings, mapperParams)

            settingsDao.update(updatedSettings)

            crossDao.clearAll(userId)
            params.categories?.let {
                crossDao.insertAll(
                    it.filter { item -> item.id != null }
                        .map { item -> SettingsCategoryCrossRef(userId, item.id!!, userId) }
                )
            }

            Result.success(Unit)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    data class Params(
        val settings: TrainingSettings,
        val categories: List<Category>?
    )
}
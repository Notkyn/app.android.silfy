package ua.notky.silfy.usecase.settings

import ua.notky.silfy.mapper.category.CategoryMapper
import ua.notky.silfy.mapper.settings.TrainingSettingsMapper
import ua.notky.silfy.models.states.ResultLoadSettingsWithCategories
import ua.notky.silfy.repository.db.dao.SettingsDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class LoadSettingsWithCategoryUseCase @Inject constructor(
    private val dataStore: AppDataStorePreferences,
    private val settingsDao: SettingsDao
) {

    suspend fun load(): ResultLoadSettingsWithCategories {
        return try {
            val userId = dataStore.getProfileId()
                ?: throw IllegalStateException("User is missing")

            val data = settingsDao.getWithCategories(userId)
                ?: throw IllegalStateException("Data not found")

            val settings = TrainingSettingsMapper.map(data.settings)
            val categories = CategoryMapper.map(data.categories)

            ResultLoadSettingsWithCategories.Success(settings, categories)
        } catch (ex: Exception) {
            ex.printStackTrace()
            ResultLoadSettingsWithCategories.Failure(ex)
        }
    }
}
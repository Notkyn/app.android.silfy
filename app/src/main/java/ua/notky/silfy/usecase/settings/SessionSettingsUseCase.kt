package ua.notky.silfy.usecase.settings

import ua.notky.silfy.models.enums.DifficultType
import ua.notky.silfy.models.enums.SelectedWordsType
import ua.notky.silfy.models.enums.TrainingDurationType
import ua.notky.silfy.models.local.SettingsLocal
import ua.notky.silfy.models.local.cross.SettingsCategoryCrossRef
import ua.notky.silfy.models.model.SessionSettings
import ua.notky.silfy.repository.db.dao.SettingsDao
import ua.notky.silfy.repository.db.dao.cross.SettingsCategoryCrossDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import javax.inject.Inject

/**
 * Training settings of the active profile (4a / 6b). The table is the 1.x one, read without a migration:
 * the endless duration becomes 30 min, a typed mistake count — the closest of 3 / 5 / 10.
 */
class SessionSettingsUseCase @Inject constructor(
    private val dataStore: AppDataStorePreferences,
    private val settingsDao: SettingsDao,
    private val crossDao: SettingsCategoryCrossDao
) {

    /** Defaults when the profile has no settings yet */
    suspend fun load(): Result<SessionSettings> {
        return try {
            val userId = dataStore.getProfileId() ?: throw IllegalStateException("User is missing")
            val data = settingsDao.getWithCategories(userId)
                ?: return Result.success(SessionSettings())

            val local = data.settings
            val settings = SessionSettings(
                difficulty = DifficultType.getById(local.difficult),
                minutes = minutesOf(TrainingDurationType.getById(local.duration)),
                isMistakeLimit = local.enableErrors && local.countErrors > 0,
                maxMistakes = if (local.countErrors > 0) {
                    SessionSettings.closest(SessionSettings.MISTAKE_LIMITS, local.countErrors)
                } else {
                    SessionSettings().maxMistakes
                },
                isFavouritesOnly = SelectedWordsType.getById(local.typeWords) == SelectedWordsType.FAVOURITE,
                isBlacklistIncluded = local.isBlackList,
                // Only categories that still exist come through the relation
                categoryIds = data.categories.mapNotNull { it.id }.toSet()
            )

            Result.success(settings)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    suspend fun save(settings: SessionSettings): Result<Unit> {
        return try {
            val userId = dataStore.getProfileId() ?: throw IllegalStateException("User is missing")

            // One row per profile: settings_id = user_id, as SaveDefaultDataUseCase creates it
            settingsDao.insert(
                SettingsLocal(
                    settingsId = userId,
                    difficult = settings.difficulty.id,
                    duration = durationOf(settings.minutes).id,
                    enableErrors = settings.isMistakeLimit,
                    countErrors = settings.maxMistakes,
                    typeWords = if (settings.isFavouritesOnly) SelectedWordsType.FAVOURITE.id else SelectedWordsType.ALL.id,
                    isBlackList = settings.isBlacklistIncluded,
                    userId = userId
                )
            )

            crossDao.clearAll(userId)
            crossDao.insertAll(settings.categoryIds.map { SettingsCategoryCrossRef(userId, it, userId) })

            Result.success(Unit)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    private fun minutesOf(duration: TrainingDurationType): Int {
        return when (duration) {
            TrainingDurationType.FIVE -> 5
            TrainingDurationType.TEN -> 10
            TrainingDurationType.THIRTY, TrainingDurationType.INFINITY -> 30
        }
    }

    private fun durationOf(minutes: Int): TrainingDurationType {
        return when (minutes) {
            10 -> TrainingDurationType.TEN
            30 -> TrainingDurationType.THIRTY
            else -> TrainingDurationType.FIVE
        }
    }
}

package ua.notky.silfy.usecase.dictionary

import ua.notky.silfy.models.states.WordState
import ua.notky.silfy.repository.db.dao.word.WordDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class ClearLearningProgressUseCase @Inject constructor(
    private val dataStore: AppDataStorePreferences,
    private val wordDao: WordDao
) {

    suspend fun clear(): Result<Unit> {
        return try {
            val userId = dataStore.getProfileId() ?: throw IllegalStateException("User is missing")

            val updatedWords = wordDao.getAll(userId)
                .map {
                    it.copy(
                        state = WordState.UNKNOWN.id,
                        minCountState = WordState.UNKNOWN.minCount
                    )
                }

            wordDao.updateAll(updatedWords)

            Result.success(Unit)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }
}
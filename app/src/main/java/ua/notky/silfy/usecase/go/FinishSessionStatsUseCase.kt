package ua.notky.silfy.usecase.go

import ua.notky.silfy.repository.db.dao.SessionStatsDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 25.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class FinishSessionStatsUseCase @Inject constructor(
    private val dataStore: AppDataStorePreferences,
    private val sessionDao: SessionStatsDao
) {

    suspend fun update(params: Params): Result<Unit> {
        return try {
            val userId = dataStore.getProfileId() ?: throw IllegalStateException("User is missing")
            val sessionId = params.sessionId ?: throw IllegalStateException("Session id is missing")
            val session = sessionDao.get(sessionId, userId)
                ?: throw IllegalStateException("Session is missing")

            val updatesSession = session.copy(
                updateTime = System.currentTimeMillis(),
                finishReasonType = params.finishType
            )

            sessionDao.update(updatesSession)

            Result.success(Unit)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    data class Params(
        val sessionId: String?,
        val finishType: Int
    )
}
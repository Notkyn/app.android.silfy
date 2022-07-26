package ua.notky.silfy.usecase.go

import ua.notky.silfy.models.model.ResultTraining
import ua.notky.silfy.models.states.WordState
import ua.notky.silfy.repository.db.dao.SessionStatsDao
import ua.notky.silfy.repository.db.dao.word.WordDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 25.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class UpdateSessionStatsUseCase @Inject constructor(
    private val dataStore: AppDataStorePreferences,
    private val sessionDao: SessionStatsDao,
    private val wordDao: WordDao
) {

    suspend fun update(params: Params): Result<Unit> {
        return try {
            val userId = dataStore.getProfileId() ?: throw IllegalStateException("User is missing")
            val sessionId = params.sessionId ?: throw IllegalStateException("Session id is missing")
            val wordId = params.wordId ?: throw IllegalStateException("Word is missing")

            updateSession(sessionId, userId, wordId, params.isSuccess)
            updateWord(params.wordId, userId, params.isSuccess)

            Result.success(Unit)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    private suspend fun updateSession(
        sessionId: String,
        userId: Int,
        wordId: Int,
        isSuccess: Boolean
    ) {
        val session = sessionDao.get(sessionId, userId)
            ?: throw IllegalStateException("Session is missing")

        val map = session.resultMap
        map[wordId] = fetchResultTraining(map[wordId], isSuccess)

        val updatesSession = session.copy(
            updateTime = System.currentTimeMillis(),
            resultMap = map
        )

        sessionDao.update(updatesSession)
    }

    private suspend fun updateWord(wordId: Int, userId: Int, isSuccess: Boolean) {
        val word = wordDao.findById(wordId, userId)
        word?.let {
            val count = if (isSuccess) {
                it.minCountState + 1
            } else {
                it.minCountState - 1
            }

            wordDao.update(
                it.copy(
                    minCountState = count,
                    state = WordState.getByCount(count).id
                )
            )
        }
    }

    private fun fetchResultTraining(result: ResultTraining?, isSuccess: Boolean): ResultTraining {
        val resultTemp = result ?: ResultTraining()

        return if (isSuccess) {
            resultTemp.copy(success = resultTemp.success + 1)
        } else {
            resultTemp.copy(failure = resultTemp.failure + 1)
        }
    }

    data class Params(
        val sessionId: String?,
        val wordId: Int?,
        val isSuccess: Boolean
    )
}
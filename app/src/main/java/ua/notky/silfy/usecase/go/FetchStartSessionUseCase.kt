package ua.notky.silfy.usecase.go

import ua.notky.silfy.mapper.session.SessionStatsLocalMapper
import ua.notky.silfy.mapper.word.WordMapper
import ua.notky.silfy.models.enums.GoStatsType
import ua.notky.silfy.models.model.SessionSettings
import ua.notky.silfy.models.model.TrainingSession
import ua.notky.silfy.models.model.Word
import ua.notky.silfy.models.states.FetchSessionResult
import ua.notky.silfy.repository.db.dao.SessionStatsDao
import ua.notky.silfy.repository.db.dao.word.WordDao
import ua.notky.silfy.repository.db.dao.word.WordListDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import java.util.*
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

/** Starts a training session: its words by the settings, the whole dictionary for wrong options, a stats row */
class FetchStartSessionUseCase @Inject constructor(
    private val dataStore: AppDataStorePreferences,
    private val wordDao: WordDao,
    private val wordListDao: WordListDao,
    private val sessionDao: SessionStatsDao
) {

    suspend fun fetch(settings: SessionSettings): FetchSessionResult {
        return try {
            val userId = dataStore.getProfileId() ?: throw IllegalStateException("User is missing")

            val words = WordMapper.map(
                wordListDao.getSessionWords(
                    userId = userId,
                    favouritesOnly = settings.isFavouritesOnly,
                    withBlacklist = settings.isBlacklistIncluded,
                    anyCategory = settings.categoryIds.isEmpty(),
                    categoryIds = settings.categoryIds.toList().ifEmpty { listOf(NO_CATEGORY) }
                )
            )
            if (words.isEmpty()) return FetchSessionResult.Empty

            val dictionary = WordMapper.map(wordDao.getAll(userId))
            val session = createSession(settings, words, userId)

            FetchSessionResult.Success(session, words, dictionary)
        } catch (ex: Exception) {
            ex.printStackTrace()
            FetchSessionResult.Failure(ex)
        }
    }

    private suspend fun createSession(
        settings: SessionSettings,
        words: List<Word>,
        userId: Int
    ): TrainingSession {
        val startTime = System.currentTimeMillis()
        val id = "${UUID.randomUUID()}$startTime"
        val session = TrainingSession(
            id,
            startTime,
            startTime,
            GoStatsType.NONE,
            settings.categoryIds.toList(),
            words.mapNotNull { it.id },
            HashMap()
        )

        val mapperParams = SessionStatsLocalMapper.Params(userId)
        val localSession = SessionStatsLocalMapper.map(session, mapperParams)

        sessionDao.insert(localSession)

        return session
    }

    private companion object {
        /** Keeps `IN (:categoryIds)` non-empty when every category is allowed */
        const val NO_CATEGORY = -1
    }
}

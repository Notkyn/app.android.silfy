package ua.notky.silfy.usecase.go

import ua.notky.silfy.mapper.session.SessionStatsLocalMapper
import ua.notky.silfy.models.enums.GoStatsType
import ua.notky.silfy.models.enums.SelectedWordsType
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.models.model.TrainingSession
import ua.notky.silfy.models.model.Word
import ua.notky.silfy.models.states.FetchSessionResult
import ua.notky.silfy.repository.db.dao.SessionStatsDao
import ua.notky.silfy.repository.db.dao.word.WordDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import ua.notky.silfy.util.fetchWords
import java.util.*
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class FetchStartSessionUseCase @Inject constructor(
    private val dataStore: AppDataStorePreferences,
    private val wordDao: WordDao,
    private val sessionDao: SessionStatsDao
) {

    suspend fun fetch(params: Params): FetchSessionResult {
        return try {
            val userId = dataStore.getProfileId() ?: throw IllegalStateException("User is missing")

            val words = wordDao.fetchWords(
                params.wordsType,
                params.isBlacklist,
                params.categories,
                userId
            )
            val session = fetchSession(params, words, userId)

            FetchSessionResult.Success(session, words)
        } catch (ex: Exception) {
            ex.printStackTrace()
            FetchSessionResult.Failure(ex)
        }
    }

    private suspend fun fetchSession(
        params: Params,
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
            params.categories?.mapNotNull { it.id } ?: listOf(),
            words.mapNotNull { it.id },
            HashMap()
        )

        val mapperParams = SessionStatsLocalMapper.Params(userId)
        val localSession = SessionStatsLocalMapper.map(session, mapperParams)

        sessionDao.insert(localSession)

        return session
    }

    data class Params(
        val wordsType: SelectedWordsType?,
        val isBlacklist: Boolean,
        val categories: List<Category>?
    )
}
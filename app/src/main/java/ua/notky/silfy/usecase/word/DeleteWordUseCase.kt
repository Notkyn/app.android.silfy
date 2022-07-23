package ua.notky.silfy.usecase.word

import ua.notky.silfy.repository.db.dao.WordCategoryCrossDao
import ua.notky.silfy.repository.db.dao.word.WordDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 23.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class DeleteWordUseCase @Inject constructor(
    private val dataStore: AppDataStorePreferences,
    private val wordDao: WordDao,
    private val crossDao: WordCategoryCrossDao
) {

    suspend fun delete(params: Params): Result<Unit> {
        return try {
            val userId = dataStore.getProfileId() ?: throw IllegalStateException("User is missing")
            if(params.wordId == null) throw IllegalStateException("Word id is missing")

            wordDao.remove(params.wordId, userId)
            crossDao.deleteByWord(params.wordId, userId)

            Result.success(Unit)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    data class Params(
        val wordId: Int?
    )
}
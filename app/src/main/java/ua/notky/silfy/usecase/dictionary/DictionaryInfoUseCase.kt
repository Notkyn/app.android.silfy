package ua.notky.silfy.usecase.dictionary

import ua.notky.silfy.models.model.DictionaryByStateInfo
import ua.notky.silfy.models.model.DictionaryInfo
import ua.notky.silfy.models.states.WordState
import ua.notky.silfy.repository.db.dao.DictionaryDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class DictionaryInfoUseCase @Inject constructor(
    private val dataStore: AppDataStorePreferences,
    private val dictionaryDao: DictionaryDao
) {

    suspend fun fetch(): Result<DictionaryInfo> {
        return try {
            val userId = dataStore.getProfileId() ?: throw IllegalStateException("User is missing")

            val countAll = dictionaryDao.getCountAll(userId)
            val stats = getStatsByState(userId)
            val countFavourites = dictionaryDao.getCountFavourites(userId)
            val countBlacks = dictionaryDao.getCountBlacks(userId)

            Result.success(
                DictionaryInfo(
                    countAll,
                    stats,
                    countFavourites,
                    countBlacks
                )
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    private suspend fun getStatsByState(userId: Int): List<DictionaryByStateInfo> {
        return WordState.values().map {
            val count = dictionaryDao.getCountByState(userId, it.id)
            DictionaryByStateInfo(it, count)
        }.filter {
            it.count > 0
        }
    }
}
package ua.notky.silfy.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ua.notky.silfy.mapper.WordMapper
import ua.notky.silfy.repository.db.dao.WordDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 20.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class WordRepository @Inject constructor(
    private val dataStore: AppDataStorePreferences,
    private val wordDao: WordDao
) {

    suspend fun getAll() = flow {
        val words = wordDao.getAll(dataStore.getProfileId())
            .map { WordMapper.map(it) }

        emit(words)
    }.flowOn(Dispatchers.IO)

    suspend fun getAlLFavourites() = flow {
        val words = wordDao.getAllByFavourite(dataStore.getProfileId(), true)
            .map { WordMapper.map(it) }

        emit(words)
    }.flowOn(Dispatchers.IO)

    suspend fun getAllBlacklist() = flow {
        val words = wordDao.getAllByBlacklist(dataStore.getProfileId(), true)
            .map { WordMapper.map(it) }

        emit(words)
    }.flowOn(Dispatchers.IO)
}
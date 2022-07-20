package ua.notky.silfy.usecase.word

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import ua.notky.silfy.mapper.WordDbMapper
import ua.notky.silfy.repository.db.dao.WordDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import ua.notky.silfy.util.getWordsFromAssets
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class SaveDefaultWordsUseCase @Inject constructor(
    @ApplicationContext val context: Context,
    private val dataStore: AppDataStorePreferences,
    private val wordDao: WordDao
) {

    suspend fun fetch(): Result<Unit> {
        return try {
            val userId = dataStore.getProfileId() ?: throw IllegalStateException("User is missing")

            val dtos = context.getWordsFromAssets()
                ?: throw IllegalStateException("Data is missing")

            val params = WordDbMapper.Params(userId)
            val words = WordDbMapper.map(dtos, params)

            wordDao.clearAll(userId)
            wordDao.insertAll(words)

            Result.success(Unit)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }
}
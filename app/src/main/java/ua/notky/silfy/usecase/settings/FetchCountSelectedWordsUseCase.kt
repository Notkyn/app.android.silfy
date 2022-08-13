package ua.notky.silfy.usecase.settings

import ua.notky.silfy.models.enums.SelectedWordsType
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.repository.db.dao.word.WordDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import ua.notky.silfy.util.fetchWords
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 13.08.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class FetchCountSelectedWordsUseCase @Inject constructor(
    private val dataStore: AppDataStorePreferences,
    private val wordDao: WordDao
) {
    suspend fun fetch(params: Params): Result<Int> {
        return try {
            val userId = dataStore.getProfileId() ?: throw IllegalStateException("User is missing")

            val words = wordDao.fetchWords(
                params.wordsType,
                params.isBlacklist,
                params.categories,
                userId
            )

            Result.success(words.size)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    data class Params(
        val wordsType: SelectedWordsType?,
        val isBlacklist: Boolean,
        val categories: List<Category>?
    )
}
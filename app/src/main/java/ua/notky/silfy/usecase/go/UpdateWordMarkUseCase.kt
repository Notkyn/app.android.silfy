package ua.notky.silfy.usecase.go

import ua.notky.silfy.models.local.WordLocal
import ua.notky.silfy.models.model.Word
import ua.notky.silfy.repository.db.dao.word.WordDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 13.08.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class UpdateWordMarkUseCase @Inject constructor(
    private val dataStore: AppDataStorePreferences,
    private val wordDao: WordDao
) {

    suspend fun updateFavourite(params: Params): Result<Unit> {
        return try {
            run(params, true)

            Result.success(Unit)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    suspend fun updateBlack(params: Params): Result<Unit> {
        return try {
            run(params, false)

            Result.success(Unit)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    private suspend fun run(params: Params, isFavourite: Boolean) {
        runTask(params) {
            if(isFavourite) {
                it.copy(isFavourite = params.checked)
            } else {
                it.copy(isBlacklist = params.checked)
            }
        }

        val word = params.words.firstOrNull { it.id == params.wordId }
        word?.let {
            params.words.remove(it)
            val newWord = if(isFavourite) {
                it.copy(isFavourite = params.checked)
            } else {
                it.copy(isBlacklist = params.checked)
            }
            params.words.add(newWord)
        }
    }

    private suspend fun runTask(params: Params, block: (WordLocal) -> WordLocal) {
        val wordId = params.wordId ?: throw IllegalStateException("Word Id is missing")
        val userId = dataStore.getProfileId() ?: throw IllegalStateException("User ID is missing")
        val word = wordDao.findById(wordId, userId) ?: throw IllegalStateException("Word Not Found")

        val updatedWord = block.invoke(word)

        wordDao.update(updatedWord)
    }

    data class Params(
        val wordId: Int?,
        val checked: Boolean,
        val words: MutableList<Word>
    )
}
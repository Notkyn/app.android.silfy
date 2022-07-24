package ua.notky.silfy.usecase.word

import ua.notky.silfy.mapper.word.WordLocalMapper
import ua.notky.silfy.models.local.WordLocal
import ua.notky.silfy.models.local.cross.WordCategoryCrossRef
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.models.model.Word
import ua.notky.silfy.repository.db.dao.WordCategoryCrossDao
import ua.notky.silfy.repository.db.dao.word.WordDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 23.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class SaveWordUseCase @Inject constructor(
    private val dataStore: AppDataStorePreferences,
    private val wordDao: WordDao,
    private val crossDao: WordCategoryCrossDao
) {

    suspend fun save(params: Params): Result<Unit> {
        return try {
            val userId = dataStore.getProfileId() ?: throw IllegalStateException("User is missing")

            val mapperParams = WordLocalMapper.Params(userId)
            val wordData = WordLocalMapper.map(params.word, mapperParams)

            val wordId = if (params.word.isNew()) {
                insert(wordData, userId)
            } else {
                update(wordData)
            }

            wordId?.let { saveCrossRefs(it, userId, params.categories) }

            Result.success(Unit)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    private suspend fun insert(data: WordLocal, userId: Int): Int? {
        wordDao.insert(data)
        val actualWord = wordDao.getOne(data.en, userId)

        return actualWord?.id
    }

    private suspend fun update(data: WordLocal): Int? {
        wordDao.update(data)

        return data.id
    }

    private suspend fun saveCrossRefs(wordId: Int, userId: Int, categories: List<Category>) {
        crossDao.deleteByWord(wordId, userId)

        val crossRefs = categories.mapNotNull { category ->
            category.id?.let {
                WordCategoryCrossRef(
                    wordId,
                    it,
                    userId
                )
            }
        }

        crossDao.insertAll(crossRefs)
    }

    data class Params(
        val word: Word,
        val categories: List<Category>
    )
}
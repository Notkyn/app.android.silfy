package ua.notky.silfy.usecase.word

import ua.notky.silfy.mapper.word.WordLocalMapper
import ua.notky.silfy.models.local.WordLocal
import ua.notky.silfy.models.local.cross.WordCategoryCrossRef
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.models.model.Word
import ua.notky.silfy.repository.db.dao.cross.WordCategoryCrossDao
import ua.notky.silfy.repository.db.dao.word.WordDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import ua.notky.silfy.util.normalizeTranslation
import ua.notky.silfy.util.normalizeWordEn
import javax.inject.Inject

class WordExistsException(en: String) : IllegalStateException("Word \"$en\" already exists")

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

    /** Fails with [WordExistsException] when the profile already has this English word (ignoring case) */
    suspend fun save(params: Params): Result<Unit> {
        return try {
            val userId = dataStore.getProfileId() ?: throw IllegalStateException("User is missing")

            val word = params.word.copy(
                en = normalizeWordEn(params.word.en),
                translation = normalizeTranslation(params.word.translation)
            )

            val existing = wordDao.findByEnIgnoreCase(word.en, userId)
            if (existing != null && existing.id != word.id) throw WordExistsException(word.en)

            val mapperParams = WordLocalMapper.Params(userId)
            val wordData = WordLocalMapper.map(word, mapperParams)

            val wordId = if (word.isNew()) {
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
        val actualWord = wordDao.findByEn(data.en, userId)

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
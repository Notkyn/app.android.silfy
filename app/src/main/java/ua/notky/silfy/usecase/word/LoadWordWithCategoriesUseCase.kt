package ua.notky.silfy.usecase.word

import ua.notky.silfy.mapper.category.CategoryMapper
import ua.notky.silfy.mapper.word.WordMapper
import ua.notky.silfy.models.states.ResultLoadWordWithCategories
import ua.notky.silfy.repository.db.dao.word.WordDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 23.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class LoadWordWithCategoriesUseCase @Inject constructor(
    private val dataStore: AppDataStorePreferences,
    private val wordDao: WordDao
) {
    suspend fun load(params: Params): ResultLoadWordWithCategories {
        return try {
            val userId = dataStore.getProfileId()

            if (userId == null) throw IllegalStateException("User is missing")
            if (params.wordId == null) throw IllegalStateException("Word id is missing")

            val data = wordDao.getOneWithCategories(params.wordId, userId)
                ?: throw IllegalStateException("Data not found")

            val word = WordMapper.map(data.word)
            val categories = CategoryMapper.map(data.categories)

            ResultLoadWordWithCategories.Success(word, categories)
        } catch (ex: Exception) {
            ex.printStackTrace()
            ResultLoadWordWithCategories.Failure(ex)
        }
    }

    data class Params(
        val wordId: Int?
    )
}
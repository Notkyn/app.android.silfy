package ua.notky.silfy.usecase.word

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import ua.notky.silfy.mapper.category.CategoryDataMapper
import ua.notky.silfy.mapper.word.WordDataMapper
import ua.notky.silfy.models.dto.WordDto
import ua.notky.silfy.models.local.CategoryData
import ua.notky.silfy.models.local.WordData
import ua.notky.silfy.models.local.cross.WordCategoryCrossRef
import ua.notky.silfy.repository.db.dao.CategoryDao
import ua.notky.silfy.repository.db.dao.WordCategoryCrossDao
import ua.notky.silfy.repository.db.dao.word.WordDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import ua.notky.silfy.util.getCategoriesFromAssets
import ua.notky.silfy.util.getWordsFromAssets
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class SaveDefaultDataUseCase @Inject constructor(
    @ApplicationContext val context: Context,
    private val dataStore: AppDataStorePreferences,
    private val wordDao: WordDao,
    private val categoryDao: CategoryDao,
    private val crossRefsDao: WordCategoryCrossDao
) {

    suspend fun fetch(): Result<Unit> {
        return try {
            val userId = dataStore.getProfileId() ?: throw IllegalStateException("User is missing")

            val wordDtos = context.getWordsFromAssets()
                ?: throw IllegalStateException("Data is missing")
            val words = fetchWords(userId, wordDtos)
            val categories = fetchCategories(userId)

            wordDao.replaceAll(userId, words)
            categoryDao.replaceAll(userId, categories)

            val wordData = wordDao.getAll(userId)
            val categoryData = categoryDao.getAll(userId)
            val crossRefs = fetchAllCrossRefs(wordData, wordDtos, categoryData, userId)

            crossRefsDao.replaceAll(userId, crossRefs)
            Result.success(Unit)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    private fun fetchWords(userId: Int, wordDtos: List<WordDto>): List<WordData> {
        val params = WordDataMapper.Params(userId)
        return WordDataMapper.map(wordDtos, params)
    }

    private fun fetchCategories(userId: Int): List<CategoryData> {
        val categoryDtos = context.getCategoriesFromAssets()
            ?: throw IllegalStateException("Data is missing")

        val params = CategoryDataMapper.Params(userId)
        return CategoryDataMapper.map(categoryDtos, params)
    }

    private fun fetchAllCrossRefs(
        words: List<WordData>,
        wordDtos: List<WordDto>,
        categories: List<CategoryData>,
        userId: Int
    ): List<WordCategoryCrossRef> {
        val crossRefs: MutableList<WordCategoryCrossRef> = mutableListOf()

        words.forEach { word ->
            word.id?.let {
                val dto = wordDtos.firstOrNull { word.en == it.en }

                dto?.categories?.forEach { categoryName ->
                    val selectCategory = categories.firstOrNull { it.title == categoryName }

                    selectCategory?.id?.let {
                        crossRefs.add(WordCategoryCrossRef(word.id, selectCategory.id, userId))
                    }
                }
            }
        }

        return crossRefs
    }
}
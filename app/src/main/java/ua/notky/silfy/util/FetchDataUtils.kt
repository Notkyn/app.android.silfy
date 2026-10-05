package ua.notky.silfy.util

import android.content.Context
import ua.notky.silfy.mapper.category.CategoryLocalMapper
import ua.notky.silfy.mapper.word.WordLocalMapper
import ua.notky.silfy.models.dto.CategoryDto
import ua.notky.silfy.models.dto.WordDto
import ua.notky.silfy.models.enums.AppLanguage
import ua.notky.silfy.models.local.CategoryLocal
import ua.notky.silfy.models.local.WordLocal
import ua.notky.silfy.models.local.cross.WordCategoryCrossRef

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 13.08.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

/** Words without a translation into [language] are skipped */
fun List<WordDto>.mapToLocal(userId: Int, language: AppLanguage): List<WordLocal> {
    val params = WordLocalMapper.Params(userId, language)
    return WordLocalMapper.map(this.filter { it.translation(language) != null }, params)
}

fun Context.fetchCategoryDtos(): List<CategoryDto> {
    return this.getCategoriesFromAssets() ?: throw IllegalStateException("Data is missing")
}

fun List<CategoryDto>.toCategoriesLocal(userId: Int, language: AppLanguage): List<CategoryLocal> {
    val params = CategoryLocalMapper.Params(userId, language)
    return CategoryLocalMapper.map(this, params)
}

fun fetchAllCrossRefs(
    words: List<WordLocal>,
    wordDtos: List<WordDto>,
    categoryDtos: List<CategoryDto>,
    categories: List<CategoryLocal>,
    language: AppLanguage,
    userId: Int
): List<WordCategoryCrossRef> {
    val crossRefs: MutableList<WordCategoryCrossRef> = mutableListOf()

    val titleByKey = categoryDtos.associate { it.key to it.title(language) }
    val categoryByTitle = categories.associateBy { it.title }
    // Saved words are capitalized ("Apple"), the assets are not ("apple")
    val dtoByEn = wordDtos.associateBy { it.en.trim().lowercase() }

    words.forEach { word ->
        val wordId = word.id ?: return@forEach

        dtoByEn[word.en.trim().lowercase()]?.categories?.forEach { categoryKey ->
            val categoryId = titleByKey[categoryKey]?.let { categoryByTitle[it] }?.id

            categoryId?.let { crossRefs.add(WordCategoryCrossRef(wordId, it, userId)) }
        }
    }

    return crossRefs
}

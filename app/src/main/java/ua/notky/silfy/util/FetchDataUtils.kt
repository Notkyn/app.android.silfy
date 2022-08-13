package ua.notky.silfy.util

import android.content.Context
import ua.notky.silfy.mapper.category.CategoryLocalMapper
import ua.notky.silfy.mapper.word.WordLocalMapper
import ua.notky.silfy.models.dto.WordDto
import ua.notky.silfy.models.local.CategoryLocal
import ua.notky.silfy.models.local.WordLocal
import ua.notky.silfy.models.local.cross.WordCategoryCrossRef

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 13.08.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

fun List<WordDto>.mapToLocal(userId: Int): List<WordLocal> {
    val params = WordLocalMapper.Params(userId)
    return WordLocalMapper.map(this, params)
}

fun Context.fetchCategories(userId: Int): List<CategoryLocal> {
    val categoryDtos = this.getCategoriesFromAssets()
        ?: throw IllegalStateException("Data is missing")

    val params = CategoryLocalMapper.Params(userId)
    return CategoryLocalMapper.map(categoryDtos, params)
}

fun fetchAllCrossRefs(
    words: List<WordLocal>,
    wordDtos: List<WordDto>,
    categories: List<CategoryLocal>,
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
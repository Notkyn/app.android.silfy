package ua.notky.silfy.util

import ua.notky.silfy.mapper.word.WordMapper
import ua.notky.silfy.models.enums.SelectedWordsType
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.models.model.Word
import ua.notky.silfy.repository.db.dao.word.WordDao

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 13.08.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

suspend fun WordDao.fetchWords(
    wordsType: SelectedWordsType?,
    isBlacklist: Boolean,
    categories: List<Category>?,
    userId: Int
): List<Word> {
    val isFavourite = wordsType == SelectedWordsType.FAVOURITE
    val categoryIds = categories?.mapNotNull { it.id } ?: listOf()

    val localWordsWithCategories = when {
        isFavourite && isBlacklist -> this.getFavouritesWithCategoriesAndBlacks(userId)
        isFavourite && !isBlacklist -> this.getFavouritesWithCategoriesWithoutBlacks(userId)
        !isFavourite && isBlacklist -> this.getAllWithCategoriesAndBlacks(userId)
        else -> this.getAllWithCategoriesWithoutBlacks(userId)
    }

    val filterList = if (categoryIds.isEmpty()) {
        localWordsWithCategories
    } else {
        localWordsWithCategories.filter { item ->
            item.categories.any { categoryIds.contains(it.id) }
        }
    }

    return filterList.map { WordMapper.map(it.word) }
}
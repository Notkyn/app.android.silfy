package ua.notky.silfy.models.local.cross

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ua.notky.silfy.models.local.CategoryData
import ua.notky.silfy.models.local.WordData

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 23.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

data class WordWithCategories(
    @Embedded val word: WordData,
    @Relation(
        parentColumn = "word_id",
        entityColumn = "category_id",
        associateBy = Junction(WordCategoryCrossRef::class)
    )
    val categories: List<CategoryData>
)

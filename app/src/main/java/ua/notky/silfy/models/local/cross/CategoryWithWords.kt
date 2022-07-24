package ua.notky.silfy.models.local.cross

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ua.notky.silfy.models.local.CategoryLocal
import ua.notky.silfy.models.local.WordLocal

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 23.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

data class CategoryWithWords(
    @Embedded val category: CategoryLocal,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "word_id",
        associateBy = Junction(WordCategoryCrossRef::class)
    )
    val words: List<WordLocal>
)
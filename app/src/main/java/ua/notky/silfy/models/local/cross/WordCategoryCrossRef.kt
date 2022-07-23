package ua.notky.silfy.models.local.cross

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 23.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@Entity(
    tableName = "word_category_cross",
    primaryKeys = ["word_id", "category_id"],
    indices = [
        Index(value = ["word_id", "category_id", "user_id"], unique = true)
    ]
)
data class WordCategoryCrossRef(

    @ColumnInfo(name = "word_id")
    val wordId: Int,

    @ColumnInfo(name = "category_id")
    val categoryId: Int,

    @ColumnInfo(name = "user_id")
    val userId: Int
)
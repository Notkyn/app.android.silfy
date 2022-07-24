package ua.notky.silfy.models.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 23.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@Entity(
    tableName = "category",
    indices = [
        Index(value = ["category_id"], unique = true),
        Index(value = ["category_id", "title", "user_id"], unique = true)
    ]
)
data class CategoryLocal(
    @PrimaryKey
    @ColumnInfo(name = "category_id")
    val id: Int? = null,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "user_id")
    val userId: Int
)

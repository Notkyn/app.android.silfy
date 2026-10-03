package ua.notky.silfy.models.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@Entity(
    tableName = "word",
    indices = [
        Index(value = ["word_id"], unique = true),
        Index(value = ["en", "user_id"], unique = true)
    ]
)
data class WordLocal(
    @PrimaryKey
    @ColumnInfo(name = "word_id")
    val id: Int? = null,

    @ColumnInfo(name = "en")
    val en: String,

    @ColumnInfo(name = "translation")
    /** Translation in the profile language */
    val translation: String,

    @ColumnInfo(name = "state")
    val state: Int,

    @ColumnInfo(name = "min_count_state")
    val minCountState: Int,

    @ColumnInfo(name = "favourite")
    val isFavourite: Boolean = false,

    @ColumnInfo(name = "black")
    val isBlacklist: Boolean = false,

    @ColumnInfo(name = "user_id")
    val userId: Int
)
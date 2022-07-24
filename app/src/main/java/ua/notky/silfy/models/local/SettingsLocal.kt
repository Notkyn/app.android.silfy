package ua.notky.silfy.models.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@Entity(
    tableName = "settings",
    indices = [
        Index("user_id", unique = true),
        Index("settings_id", "user_id", unique = true)
    ]
)
data class SettingsLocal(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "settings_id")
    val settingsId: Int,

    @ColumnInfo(name = "difficult")
    val difficult: Int,

    @ColumnInfo(name = "duration")
    val duration: Int,

    @ColumnInfo(name = "enable_errors")
    val enableErrors: Boolean,

    @ColumnInfo(name = "count_errors")
    val countErrors: Int,

    @ColumnInfo(name = "type_words")
    val typeWords: Int,

    @ColumnInfo(name = "is_black_list")
    val isBlackList: Boolean,

    @ColumnInfo(name = "user_id")
    val userId: Int,
)

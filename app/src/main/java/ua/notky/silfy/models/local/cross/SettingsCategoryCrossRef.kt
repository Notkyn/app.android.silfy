package ua.notky.silfy.models.local.cross

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@Entity(
    tableName = "settings_category_cross",
    primaryKeys = ["settings_id", "category_id"],
    indices = [
        // todo index for category_id and user_id
        Index(value = ["settings_id", "category_id", "user_id"], unique = true)
    ]
)
data class SettingsCategoryCrossRef(
    @ColumnInfo(name = "settings_id")
    val settingsId: Int,

    @ColumnInfo(name = "category_id")
    val categoryId: Int,

    @ColumnInfo(name = "user_id")
    val userId: Int
)

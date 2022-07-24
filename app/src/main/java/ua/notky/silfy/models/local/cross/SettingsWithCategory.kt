package ua.notky.silfy.models.local.cross

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ua.notky.silfy.models.local.CategoryLocal
import ua.notky.silfy.models.local.SettingsLocal

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
data class SettingsWithCategory(
    @Embedded val settings: SettingsLocal,
    @Relation(
        parentColumn = "settings_id",
        entityColumn = "category_id",
        associateBy = Junction(SettingsCategoryCrossRef::class)
    )
    val categories: List<CategoryLocal>
)

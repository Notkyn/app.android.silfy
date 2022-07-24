package ua.notky.silfy.models.states

import ua.notky.silfy.models.model.Category
import ua.notky.silfy.models.model.TrainingSettings

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
sealed class ResultLoadSettingsWithCategories {
    data class Success(
        val settings: TrainingSettings,
        val categories: List<Category>
    ) : ResultLoadSettingsWithCategories()

    data class Failure(val error: Throwable?) : ResultLoadSettingsWithCategories()
}

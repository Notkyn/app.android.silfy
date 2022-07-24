package ua.notky.silfy.mapper.settings

import ua.notky.silfy.mapper.Mapper
import ua.notky.silfy.models.enums.DifficultType
import ua.notky.silfy.models.enums.SelectedWordsType
import ua.notky.silfy.models.enums.TrainingDurationType
import ua.notky.silfy.models.local.SettingsLocal
import ua.notky.silfy.models.model.TrainingSettings

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
object TrainingSettingsMapper : Mapper<TrainingSettings> {
    override fun map(input: Any): TrainingSettings {
        return when (input) {
            is SettingsLocal -> map(input)
            else -> throw IllegalStateException("Not be cast ${input::class.java.simpleName} to TrainingSettings")
        }
    }

    private fun map(input: SettingsLocal): TrainingSettings {
        return TrainingSettings(
            DifficultType.getById(input.difficult),
            TrainingDurationType.getById(input.duration),
            input.enableErrors,
            input.countErrors.toString(),
            SelectedWordsType.getById(input.typeWords),
            input.isBlackList
        )
    }
}
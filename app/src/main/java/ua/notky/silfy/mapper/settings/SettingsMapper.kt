package ua.notky.silfy.mapper.settings

import ua.notky.silfy.models.enums.DifficultType
import ua.notky.silfy.models.enums.SelectedWordsType
import ua.notky.silfy.models.enums.TrainingDurationType
import ua.notky.silfy.models.local.SettingsLocal
import ua.notky.silfy.models.model.TrainingSettings
import ua.notky.silfy.extension.parseToInt

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
object SettingsMapper {

    fun map(input: Any, params: Params): SettingsLocal {
        return when (input) {
            is TrainingSettings -> map(input, params.userId)
            else -> throw IllegalStateException("Not be cast ${input::class.java.simpleName} to SettingsLocal")
        }
    }

    data class Params(
        val userId: Int
    )

    private fun map(input: TrainingSettings, userId: Int): SettingsLocal {
        return SettingsLocal(
            userId,
            input.difficult?.id ?: DifficultType.EASY.id,
            input.duration?.id ?: TrainingDurationType.FIVE.id,
            input.enableErrors ?: false,
            input.countErrors.parseToInt(),
            input.selectWords?.id ?: SelectedWordsType.ALL.id,
            input.enableUseBlackList ?: false,
            userId
        )
    }
}
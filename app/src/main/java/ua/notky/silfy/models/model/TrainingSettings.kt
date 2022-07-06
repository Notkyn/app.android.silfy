package ua.notky.silfy.models.model

import ua.notky.silfy.models.enums.DifficultType
import ua.notky.silfy.models.enums.SelectedWordsType
import ua.notky.silfy.models.enums.TrainingDurationType

/**
 * @project Silfy
 * @author Evgeniy Zarechnyi on 06.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
data class TrainingSettings(
    val difficult: DifficultType?,
    val duration: TrainingDurationType?,
    val enableErrors: Boolean?,
    val countErrors: String?,
    val selectWords: SelectedWordsType?,
    val enableUseBlackList: Boolean?
)

package ua.notky.silfy.models.observable

import androidx.databinding.ObservableField
import ua.notky.silfy.models.enums.DifficultType

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 02.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

data class TrainingSettingsModel(
    val difficult: ObservableField<DifficultType> = ObservableField(DifficultType.EASY)
)
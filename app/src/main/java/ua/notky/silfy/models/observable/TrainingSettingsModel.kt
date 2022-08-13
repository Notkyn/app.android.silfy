package ua.notky.silfy.models.observable

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import ua.notky.silfy.models.enums.DifficultType
import ua.notky.silfy.models.enums.MenuHeaderType
import ua.notky.silfy.models.enums.SelectedWordsType
import ua.notky.silfy.models.enums.TrainingDurationType

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 02.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

data class TrainingSettingsModel(
    val difficult: ObservableField<DifficultType> = ObservableField(DifficultType.EASY),
    val duration: ObservableField<TrainingDurationType> = ObservableField(TrainingDurationType.FIVE),
    val enableErrors: ObservableBoolean = ObservableBoolean(false),
    val countErrors: ObservableField<String> = ObservableField(""),
    val selectWordsType: ObservableField<SelectedWordsType> = ObservableField(SelectedWordsType.ALL),
    val countSelectedWords: ObservableInt = ObservableInt(0),
    val updatingCountWords: ObservableBoolean = ObservableBoolean(false),
    val enableUseBlackList: ObservableBoolean = ObservableBoolean(false),
    val isChangedSettings: ObservableBoolean = ObservableBoolean(false),
    val displayMode: ObservableField<MenuHeaderType> = ObservableField(MenuHeaderType.TRAINING)
)
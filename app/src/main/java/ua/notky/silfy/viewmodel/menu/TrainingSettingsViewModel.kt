package ua.notky.silfy.viewmodel.menu

import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.models.enums.DifficultType
import ua.notky.silfy.models.enums.SelectedWordsType
import ua.notky.silfy.models.enums.TrainingDurationType
import ua.notky.silfy.models.observable.TrainingSettingsModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 03.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class TrainingSettingsViewModel : BaseViewModel() {
    val model = TrainingSettingsModel()

    fun updateDifficult(type: DifficultType) {
        model.difficult.set(type)
    }

    fun updateDuration(type: TrainingDurationType) {
        model.duration.set(type)
    }

    fun updateSelectWords(type: SelectedWordsType) {
        model.selectWords.set(type)
    }
}
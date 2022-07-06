package ua.notky.silfy.viewmodel.menu

import ua.notky.base.extension.observeChanged
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.models.enums.DifficultType
import ua.notky.silfy.models.enums.SelectedWordsType
import ua.notky.silfy.models.enums.TrainingDurationType
import ua.notky.silfy.models.model.TrainingSettings
import ua.notky.silfy.models.observable.TrainingSettingsModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 03.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class TrainingSettingsViewModel : BaseViewModel() {
    val model = TrainingSettingsModel()
    private var cachedModel: TrainingSettings? = null

    fun fetchData() {
        updatedCacheModel()
        checkChangedState()
    }

    private fun updatedCacheModel() {
        cachedModel = TrainingSettings(
            model.difficult.get(),
            model.duration.get(),
            model.enableErrors.get(),
            model.countErrors.get(),
            model.selectWords.get(),
            model.enableUseBlackList.get()
        )

        observeChanged(model.difficult, ::checkChangedState)
        observeChanged(model.duration, ::checkChangedState)
        observeChanged(model.countErrors, ::checkChangedState)
        observeChanged(model.selectWords, ::checkChangedState)
        observeChanged(model.enableErrors, ::checkChangedState)
        observeChanged(model.enableUseBlackList, ::checkChangedState)
    }

    private fun checkChangedState() {
        val state = (cachedModel?.difficult != model.difficult.get()
                || cachedModel?.duration != model.duration.get()
                || cachedModel?.enableErrors != model.enableErrors.get()
                || cachedModel?.countErrors != model.countErrors.get())
                || (model.enableErrors.get() && cachedModel?.countErrors != model.countErrors.get())
                || cachedModel?.selectWords != model.selectWords.get()
                || cachedModel?.enableUseBlackList != model.enableUseBlackList.get()

        model.isChangedSettings.set(state)
    }

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
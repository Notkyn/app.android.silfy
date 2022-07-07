package ua.notky.silfy.viewmodel.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ua.notky.base.extension.observeChanged
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.models.enums.DifficultType
import ua.notky.silfy.models.enums.SelectedWordsType
import ua.notky.silfy.models.enums.TrainingDurationType
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.models.model.TrainingSettings
import ua.notky.silfy.models.observable.TrainingSettingsModel
import ua.notky.silfy.ui.extension.compareNullable
import ua.notky.silfy.util.help.getTempCategories

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 03.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class TrainingSettingsViewModel : BaseViewModel() {
    val model = TrainingSettingsModel()
    private var cachedModel: TrainingSettings? = null
    private var oldCategories: List<Category>? = null

    private val _categories: MutableLiveData<List<Category>> = MutableLiveData()
    val categories: LiveData<List<Category>> = _categories

    fun fetchData() {
        updatedCacheModel()
        checkChangedState()
        fetchCategories()
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

    fun checkChangedState() {
        val state = (cachedModel?.difficult != model.difficult.get()
                || cachedModel?.duration != model.duration.get()
                || cachedModel?.enableErrors != model.enableErrors.get()
                || cachedModel?.countErrors != model.countErrors.get())
                || (model.enableErrors.get() && cachedModel?.countErrors != model.countErrors.get())
                || cachedModel?.selectWords != model.selectWords.get()
                || cachedModel?.enableUseBlackList != model.enableUseBlackList.get()
                || !oldCategories.compareNullable(_categories.value)

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

    private fun fetchCategories() {
        oldCategories = getTempCategories(10)
        _categories.postValue(oldCategories)
    }

    fun onDeleteCategory(category: Category) {
        _categories.postValue(
            _categories.value?.filter { it.id != category.id }
        )
    }

    fun addCategory(category: Category) {
        if (_categories.value != null && !_categories.value!!.contains(category)) {
            val list: MutableList<Category> = mutableListOf()
            _categories.value?.let { list.addAll(it) }
            list.add(category)
            _categories.postValue(list)
        }
    }

    fun onSave() {
        cachedModel = TrainingSettings(
            model.difficult.get(),
            model.duration.get(),
            model.enableErrors.get(),
            model.countErrors.get(),
            model.selectWords.get(),
            model.enableUseBlackList.get()
        )

        oldCategories = _categories.value

        checkChangedState()
    }
}
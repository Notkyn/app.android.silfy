package ua.notky.silfy.viewmodel.go

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.mapper.category.CategoryMapper
import ua.notky.silfy.models.enums.AppLanguage
import ua.notky.silfy.models.enums.DifficultType
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.models.model.SessionSettings
import ua.notky.silfy.repository.db.dao.CategoryDao
import ua.notky.silfy.repository.db.dao.ProfileDao
import ua.notky.silfy.repository.db.dao.word.WordListDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import ua.notky.silfy.usecase.settings.SessionSettingsUseCase
import javax.inject.Inject

/**
 * 4a New session / 6b Training mode: the same settings cards, "Start" or "Save settings".
 * Scoped to SessionSetupFragment. Settings are saved in both modes.
 */
@HiltViewModel
class SessionSetupViewModel @Inject constructor(
    private val dataStore: AppDataStorePreferences,
    private val profileDao: ProfileDao,
    private val categoryDao: CategoryDao,
    private val wordListDao: WordListDao,
    private val settingsUseCase: SessionSettingsUseCase
) : BaseViewModel() {

    sealed class Event {
        object Idle : Event()
        object Saving : Event()
        /** Start: settings saved, words match */
        object Started : Event()
        /** Save settings: done */
        object Saved : Event()
        /** 4b "No words match" */
        object Empty : Event()
        object Failure : Event()
    }

    private val profileId = MutableLiveData<Int>()

    private val _settings = MutableLiveData<SessionSettings>()
    val settings: LiveData<SessionSettings> = _settings

    /** Profile language: "English → Українська" under Easy */
    private val _language = MutableLiveData<AppLanguage>()
    val language: LiveData<AppLanguage> = _language

    /** All categories of the profile for the chips, in the order of creation */
    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val countFilter = MediatorLiveData<CountFilter>().apply {
        fun update() {
            val id = profileId.value ?: return
            val settings = _settings.value ?: return
            value = CountFilter(id, settings.isFavouritesOnly, settings.isBlacklistIncluded, settings.categoryIds)
        }
        addSource(profileId) { update() }
        addSource(_settings) { update() }
    }

    /** "{n} words match these settings": only filter changes query again, not difficulty or duration */
    val matchCount: LiveData<Int> = countFilter.distinctUntilChanged().switchMap { filter ->
        wordListDao.countSessionWords(
            userId = filter.profileId,
            favouritesOnly = filter.isFavouritesOnly,
            withBlacklist = filter.isBlacklistIncluded,
            anyCategory = filter.categoryIds.isEmpty(),
            categoryIds = filter.categoryIds.toList().ifEmpty { listOf(NO_CATEGORY) }
        )
    }

    private val _event = MutableLiveData<Event>(Event.Idle)
    val event: LiveData<Event> = _event

    init {
        viewModelScope.launch {
            val id = dataStore.getProfileId() ?: return@launch
            profileDao.getById(id)?.let { _language.value = it.language }
            val categories = CategoryMapper.map(categoryDao.getAll(id))
            _categories.value = categories

            val saved = settingsUseCase.load().getOrNull() ?: SessionSettings()
            // A category deleted since then is not selected any more
            val existing = categories.mapNotNull { it.id }.toSet()
            _settings.value = saved.copy(categoryIds = saved.categoryIds intersect existing)
            profileId.value = id
        }
    }

    fun setDifficulty(value: DifficultType) = update { it.copy(difficulty = value) }

    fun setMinutes(value: Int) = update { it.copy(minutes = value) }

    fun setMistakeLimit(enabled: Boolean) = update { it.copy(isMistakeLimit = enabled) }

    fun setMaxMistakes(value: Int) = update { it.copy(maxMistakes = value) }

    fun setFavouritesOnly(value: Boolean) = update { it.copy(isFavouritesOnly = value) }

    fun setBlacklistIncluded(value: Boolean) = update { it.copy(isBlacklistIncluded = value) }

    fun toggleCategory(id: Int) = update {
        it.copy(categoryIds = if (id in it.categoryIds) it.categoryIds - id else it.categoryIds + id)
    }

    /** Saves the settings; [isMenu] — 6b "Save settings", otherwise 4a "Start" (needs at least one word) */
    fun submit(isMenu: Boolean) {
        val settings = _settings.value ?: return
        if (_event.value != Event.Idle) return

        if (!isMenu && matchCount.value == 0) {
            _event.value = Event.Empty
            return
        }

        _event.value = Event.Saving
        viewModelScope.launch {
            val result = settingsUseCase.save(settings)
            _event.value = when {
                result.isFailure -> Event.Failure
                isMenu -> Event.Saved
                else -> Event.Started
            }
        }
    }

    /** Dialogs and navigation happen once */
    fun consumeEvent() {
        _event.value = Event.Idle
    }

    private fun update(change: (SessionSettings) -> SessionSettings) {
        val current = _settings.value ?: return
        val updated = change(current)
        if (updated != current) _settings.value = updated
    }

    private data class CountFilter(
        val profileId: Int,
        val isFavouritesOnly: Boolean,
        val isBlacklistIncluded: Boolean,
        val categoryIds: Set<Int>
    )

    private companion object {
        /** Keeps `IN (:categoryIds)` non-empty when every category is allowed */
        const val NO_CATEGORY = -1
    }
}

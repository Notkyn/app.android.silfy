package ua.notky.silfy.viewmodel.words

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.mapper.word.WordMapper
import ua.notky.silfy.models.enums.DictionaryTab
import ua.notky.silfy.models.enums.WordSortMode
import ua.notky.silfy.models.model.Profile
import ua.notky.silfy.models.model.Word
import ua.notky.silfy.models.model.WordCounts
import ua.notky.silfy.repository.db.dao.ProfileDao
import ua.notky.silfy.repository.db.dao.word.WordListDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

/** 2a/2b Dictionary. Activity-scoped: tab, search and sort survive the trip to the word form */
@HiltViewModel
class WordsViewModel @Inject constructor(
    private val dataStore: AppDataStorePreferences,
    private val profileDao: ProfileDao,
    private val wordListDao: WordListDao
) : BaseViewModel() {

    data class Filter(
        val tab: DictionaryTab = DictionaryTab.ALL,
        val search: String = "",
        val sort: WordSortMode = WordSortMode.A_Z
    )

    private val profileId = MutableLiveData<Int>()

    private val _filter = MutableLiveData(Filter())
    val filter: LiveData<Filter> = _filter

    /** Live, so a renamed profile updates "Hi, {name}" */
    val profile: LiveData<Profile> = profileId.switchMap { profileDao.getLiveDataById(it) }

    val counts: LiveData<WordCounts> = profileId.switchMap { wordListDao.getCounts(it) }

    val words: LiveData<List<Word>> = profileId.switchMap { id ->
        _filter.switchMap { filter ->
            wordListDao.getWords(id, filter.tab.ordinal, filter.search.trim(), filter.sort.ordinal)
                .map { WordMapper.map(it) }
        }
    }

    init {
        viewModelScope.launch {
            dataStore.getProfileId()?.let { profileId.value = it }
        }
    }

    fun selectTab(tab: DictionaryTab) {
        updateFilter { it.copy(tab = tab) }
    }

    fun search(pattern: String) {
        updateFilter { it.copy(search = pattern) }
    }

    fun cycleSort() {
        updateFilter { it.copy(sort = it.sort.next()) }
    }

    private fun updateFilter(update: (Filter) -> Filter) {
        val current = _filter.value ?: Filter()
        val updated = update(current)
        if (updated != current) _filter.value = updated
    }
}

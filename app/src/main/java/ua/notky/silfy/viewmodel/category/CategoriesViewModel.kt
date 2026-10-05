package ua.notky.silfy.viewmodel.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.models.model.CategorySummary
import ua.notky.silfy.repository.db.dao.CategoryDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import javax.inject.Inject

/** 3a Categories grid. Live: new, renamed and deleted categories and word counts show up at once */
@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val dataStore: AppDataStorePreferences,
    private val categoryDao: CategoryDao
) : BaseViewModel() {

    private val profileId = MutableLiveData<Int>()

    val categories: LiveData<List<CategorySummary>> = profileId.switchMap { categoryDao.getSummaries(it) }

    init {
        viewModelScope.launch {
            dataStore.getProfileId()?.let { profileId.value = it }
        }
    }
}

package ua.notky.silfy.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import ua.notky.silfy.mapper.category.CategoryMapper
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.repository.db.dao.CategoryDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class CategoryRepository @Inject constructor(
    private val dataStore: AppDataStorePreferences,
    private val categoryDao: CategoryDao
) {
    private val _categoryQuery: MutableLiveData<Params> = MutableLiveData()
    val category: LiveData<List<Category>> = Transformations.switchMap(_categoryQuery) {
        val filterIds = it.categories?.mapNotNull { it.id } ?: listOf()

        categoryDao.getCategoriesWithWordsByLiveData(it.userId, filterIds)
            .map { item -> CategoryMapper.map(item) }
    }

    suspend fun loadAll(categories: List<Category>?) {
        _categoryQuery.postValue(
            Params(
                dataStore.getProfileId(),
                categories
            )
        )
    }

    data class Params(
        val userId: Int?,
        val categories: List<Category>?
    )
}
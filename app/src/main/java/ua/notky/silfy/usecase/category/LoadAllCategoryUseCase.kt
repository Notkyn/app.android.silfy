package ua.notky.silfy.usecase.category

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
class LoadAllCategoryUseCase @Inject constructor(
    private val dataStore: AppDataStorePreferences,
    private val categoryDao: CategoryDao
) {
    private val _categoriesQuery: MutableLiveData<QueryParams> = MutableLiveData()
    val categories: LiveData<List<Category>> = Transformations.switchMap(_categoriesQuery) { params ->
        val filterIds = params.categories?.mapNotNull { it.id } ?: listOf()

        categoryDao.getCategoriesWithWordsByLiveData(params.userId, filterIds)
            .map { item -> CategoryMapper.map(item) }
    }

    suspend fun load(params: Params) {
        _categoriesQuery.postValue(
            QueryParams(
                dataStore.getProfileId(),
                params.categories
            )
        )
    }

    private data class QueryParams(
        val userId: Int?,
        val categories: List<Category>?
    )

    data class Params(
        val categories: List<Category>?
    )
}
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
class LoadCategoryWithWordsUseCase @Inject constructor(
    private val dataStore: AppDataStorePreferences,
    private val categoryDao: CategoryDao
) {
    private val _categoryQuery: MutableLiveData<QueryParams> = MutableLiveData()
    val category: LiveData<Category> = Transformations.switchMap(_categoryQuery) {
        categoryDao.getCategoryWithWords(it.categoryId, it.userId)
            .map { item -> CategoryMapper.map(item) }
    }

    suspend fun load(params: Params) {
        _categoryQuery.postValue(
            QueryParams(
                params.categoryId,
                dataStore.getProfileId()
            )
        )
    }

    private data class QueryParams(
        val categoryId: Int,
        val userId: Int?
    )

    data class Params(
        val categoryId: Int
    )
}
package ua.notky.silfy.usecase.word

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import ua.notky.silfy.mapper.word.WordMapper
import ua.notky.silfy.models.enums.TabWords
import ua.notky.silfy.models.model.Word
import ua.notky.silfy.repository.db.factory.WordAllSortFactory
import ua.notky.silfy.repository.db.factory.WordBlackSortFactory
import ua.notky.silfy.repository.db.factory.WordFavouriteSortFactory
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import ua.notky.silfy.util.WordSort
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 20.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class LoadAllWordUseCase @Inject constructor(
    private val dataStore: AppDataStorePreferences,
    private val wordAllSortFactory: WordAllSortFactory,
    private val wordFavouriteSortFactory: WordFavouriteSortFactory,
    private val wordBlackSortFactory: WordBlackSortFactory
) {
    private val _wordQuery: MutableLiveData<QueryParams> = MutableLiveData()

    val words: LiveData<List<Word>> = _wordQuery.switchMap {
        when (it.indexTab) {
            TabWords.LANG.index -> {
                val params = WordAllSortFactory.Params(it.sortParams, it.searchPattern, it.userId)
                wordAllSortFactory.fetch(params).map { item -> WordMapper.map(item) }
            }
            TabWords.FAVOURITES.index -> {
                val params =
                    WordFavouriteSortFactory.Params(it.sortParams, it.searchPattern, it.userId)
                wordFavouriteSortFactory.fetch(params).map { item -> WordMapper.map(item) }
            }
            TabWords.BLACKLIST.index -> {
                val params = WordBlackSortFactory.Params(it.sortParams, it.searchPattern, it.userId)
                wordBlackSortFactory.fetch(params).map { item -> WordMapper.map(item) }
            }
            else -> MutableLiveData(listOf())
        }
    }

    private data class QueryParams(
        val indexTab: Int,
        val sortParams: WordSort.Params,
        val searchPattern: String,
        val userId: Int?
    )

    suspend fun load(params: Params) {
        _wordQuery.postValue(
            QueryParams(
                params.indexTab,
                params.sortParams,
                params.searchPattern,
                dataStore.getProfileId()
            )
        )
    }

    data class Params(
        val indexTab: Int,
        val sortParams: WordSort.Params,
        val searchPattern: String
    )
}
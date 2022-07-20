package ua.notky.silfy.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ua.notky.silfy.mapper.WordMapper
import ua.notky.silfy.repository.db.factory.WordAllSortFactory
import ua.notky.silfy.repository.db.factory.WordBlackSortFactory
import ua.notky.silfy.repository.db.factory.WordFavouriteSortFactory
import ua.notky.silfy.tools.WordSort
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 20.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class WordRepository @Inject constructor(
    private val wordAllSortFactory: WordAllSortFactory,
    private val wordFavouriteSortFactory: WordFavouriteSortFactory,
    private val wordBlackSortFactory: WordBlackSortFactory
) {
    suspend fun getAll(sortParams: WordSort.Params, searchPattern: String) = flow {
        val params = WordAllSortFactory.Params(sortParams, searchPattern)
        val words = wordAllSortFactory.fetch(params)
            .map { WordMapper.map(it) }

        emit(words)
    }.flowOn(Dispatchers.IO)

    suspend fun getAlLFavourites(sortParams: WordSort.Params, searchPattern: String) = flow {
        val params = WordFavouriteSortFactory.Params(sortParams, searchPattern)
        val words = wordFavouriteSortFactory.fetch(params)
            .map { WordMapper.map(it) }

        emit(words)
    }.flowOn(Dispatchers.IO)

    suspend fun getAllBlacklist(sortParams: WordSort.Params, searchPattern: String) = flow {
        val params = WordBlackSortFactory.Params(sortParams, searchPattern)
        val words = wordBlackSortFactory.fetch(params)
            .map { WordMapper.map(it) }

        emit(words)
    }.flowOn(Dispatchers.IO)
}
package ua.notky.silfy.repository.db.factory

import androidx.lifecycle.LiveData
import ua.notky.silfy.models.local.WordLocal
import ua.notky.silfy.models.states.SortLang
import ua.notky.silfy.models.states.SortState
import ua.notky.silfy.models.states.SortType
import ua.notky.silfy.repository.db.dao.word.WordAllSortDao
import ua.notky.silfy.util.WordSort
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 20.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class WordAllSortFactory @Inject constructor(
    private val sortDao: WordAllSortDao
) {

    fun fetch(params: Params): LiveData<List<WordLocal>> {
        return when (params.sortParams.lang) {
            SortLang.EN_UP -> getByEnUp(
                params.sortParams.type,
                params.sortParams.state,
                params.searchPattern,
                params.userId
            )
            SortLang.EN_DOWN -> getByEnDown(
                params.sortParams.type,
                params.sortParams.state,
                params.searchPattern,
                params.userId
            )
            SortLang.TRANSLATION_UP -> getByTranslationUp(
                params.sortParams.type,
                params.sortParams.state,
                params.searchPattern,
                params.userId
            )
            SortLang.TRANSLATION_DOWN -> getByTranslationDown(
                params.sortParams.type,
                params.sortParams.state,
                params.searchPattern,
                params.userId
            )
        }
    }

    private fun getByEnUp(
        type: SortType,
        state: SortState,
        search: String,
        userId: Int?
    ): LiveData<List<WordLocal>> {
        return when (type) {
            SortType.BLACKLIST -> getByEnUpTypeBlack(state, search, userId)
            SortType.FAVOURITE -> getByEnUpTypeFavourite(state, search, userId)
            SortType.DISABLE -> getByEnUpWithoutType(state, search, userId)
        }
    }

    private fun getByEnUpTypeBlack(
        state: SortState,
        search: String,
        userId: Int?
    ): LiveData<List<WordLocal>> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortEnUpStateUpBlack(
                userId,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortEnUpStateDownBlack(
                userId,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortEnUpBlack(
                userId,
                search
            )
        }
    }

    private fun getByEnUpTypeFavourite(
        state: SortState,
        search: String,
        userId: Int?
    ): LiveData<List<WordLocal>> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortEnUpStateUpFavourite(
                userId,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortEnUpStateDownFavourite(
                userId,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortEnUpFavourite(
                userId,
                search
            )
        }
    }

    private fun getByEnUpWithoutType(
        state: SortState,
        search: String,
        userId: Int?
    ): LiveData<List<WordLocal>> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortEnUpStateUp(
                userId,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortEnUpStateDown(
                userId,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortEnUp(userId, search)
        }
    }

    private fun getByEnDown(
        type: SortType,
        state: SortState,
        search: String,
        userId: Int?
    ): LiveData<List<WordLocal>> {
        return when (type) {
            SortType.BLACKLIST -> getByEnDownTypeBlack(state, search, userId)
            SortType.FAVOURITE -> getByEnDownTypeFavourite(state, search, userId)
            SortType.DISABLE -> getByEnDownWithoutType(state, search, userId)
        }
    }

    private fun getByEnDownTypeBlack(
        state: SortState,
        search: String,
        userId: Int?
    ): LiveData<List<WordLocal>> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortEnDownStateUpBlack(
                userId,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortEnDownStateDownBlack(
                userId,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortEnDownBlack(
                userId,
                search
            )
        }
    }

    private fun getByEnDownTypeFavourite(
        state: SortState,
        search: String,
        userId: Int?
    ): LiveData<List<WordLocal>> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortEnDownStateUpFavourite(
                userId,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortEnDownStateDownFavourite(
                userId,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortEnDownFavourite(
                userId,
                search
            )
        }
    }

    private fun getByEnDownWithoutType(
        state: SortState,
        search: String,
        userId: Int?
    ): LiveData<List<WordLocal>> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortEnDownStateUp(
                userId,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortEnDownStateDown(
                userId,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortEnDown(userId, search)
        }
    }

    private fun getByTranslationUp(
        type: SortType,
        state: SortState,
        search: String,
        userId: Int?
    ): LiveData<List<WordLocal>> {
        return when (type) {
            SortType.BLACKLIST -> getByTranslationUpTypeBlack(state, search, userId)
            SortType.FAVOURITE -> getByTranslationUpTypeFavourite(state, search, userId)
            SortType.DISABLE -> getByTranslationUpWithoutType(state, search, userId)
        }
    }

    private fun getByTranslationUpTypeBlack(
        state: SortState,
        search: String,
        userId: Int?
    ): LiveData<List<WordLocal>> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortTranslationUpStateUpBlack(
                userId,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortTranslationUpStateDownBlack(
                userId,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortTranslationUpBlack(
                userId,
                search
            )
        }
    }

    private fun getByTranslationUpTypeFavourite(
        state: SortState,
        search: String,
        userId: Int?
    ): LiveData<List<WordLocal>> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortTranslationUpStateUpFavourite(
                userId,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortTranslationUpStateDownFavourite(
                userId,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortTranslationUpFavourite(
                userId,
                search
            )
        }
    }

    private fun getByTranslationUpWithoutType(
        state: SortState,
        search: String,
        userId: Int?
    ): LiveData<List<WordLocal>> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortTranslationUpStateUp(
                userId,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortTranslationUpStateDown(
                userId,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortTranslationUp(userId, search)
        }
    }

    private fun getByTranslationDown(
        type: SortType,
        state: SortState,
        search: String,
        userId: Int?
    ): LiveData<List<WordLocal>> {
        return when (type) {
            SortType.BLACKLIST -> getByTranslationDownTypeBlack(state, search, userId)
            SortType.FAVOURITE -> getByTranslationDownTypeFavourite(state, search, userId)
            SortType.DISABLE -> getByTranslationDownWithoutType(state, search, userId)
        }
    }

    private fun getByTranslationDownTypeBlack(
        state: SortState,
        search: String,
        userId: Int?
    ): LiveData<List<WordLocal>> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortTranslationDownStateUpBlack(
                userId,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortTranslationDownStateDownBlack(
                userId,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortTranslationDownBlack(
                userId,
                search
            )
        }
    }

    private fun getByTranslationDownTypeFavourite(
        state: SortState,
        search: String,
        userId: Int?
    ): LiveData<List<WordLocal>> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortTranslationDownStateUpFavourite(
                userId,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortTranslationDownStateDownFavourite(
                userId,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortTranslationDownFavourite(
                userId,
                search
            )
        }
    }

    private fun getByTranslationDownWithoutType(
        state: SortState,
        search: String,
        userId: Int?
    ): LiveData<List<WordLocal>> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortTranslationDownStateUp(
                userId,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortTranslationDownStateDown(
                userId,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortTranslationDown(userId, search)
        }
    }

    data class Params(
        val sortParams: WordSort.Params,
        val searchPattern: String,
        val userId: Int?
    )
}
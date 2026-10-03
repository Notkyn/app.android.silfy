package ua.notky.silfy.repository.db.factory

import androidx.lifecycle.LiveData
import ua.notky.silfy.models.local.WordLocal
import ua.notky.silfy.models.states.SortLang
import ua.notky.silfy.models.states.SortState
import ua.notky.silfy.models.states.SortType
import ua.notky.silfy.repository.db.dao.word.WordBlackSortDao
import ua.notky.silfy.util.WordSort
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 21.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class WordBlackSortFactory @Inject constructor(
    private val sortDao: WordBlackSortDao
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
                true,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortEnUpStateDownBlack(
                userId,
                true,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortEnUpBlack(
                userId,
                true,
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
                true,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortEnUpStateDownFavourite(
                userId,
                true,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortEnUpFavourite(
                userId,
                true,
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
                true,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortEnUpStateDown(
                userId,
                true,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortEnUp(userId, true, search)
        }
    }

    private fun getByEnDown(
        type: SortType,
        state: SortState,
        search: String, userId: Int?
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
                true,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortEnDownStateDownBlack(
                userId,
                true,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortEnDownBlack(
                userId,
                true,
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
                true,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortEnDownStateDownFavourite(
                userId,
                true,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortEnDownFavourite(
                userId,
                true,
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
                true,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortEnDownStateDown(
                userId,
                true,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortEnDown(userId, true, search)
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
                true,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortTranslationUpStateDownBlack(
                userId,
                true,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortTranslationUpBlack(
                userId,
                true,
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
                true,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortTranslationUpStateDownFavourite(
                userId,
                true,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortTranslationUpFavourite(
                userId,
                true,
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
                true,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortTranslationUpStateDown(
                userId,
                true,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortTranslationUp(userId, true, search)
        }
    }

    private fun getByTranslationDown(
        type: SortType,
        state: SortState,
        search: String, userId: Int?
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
                true,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortTranslationDownStateDownBlack(
                userId,
                true,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortTranslationDownBlack(
                userId,
                true,
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
                true,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortTranslationDownStateDownFavourite(
                userId,
                true,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortTranslationDownFavourite(
                userId,
                true,
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
                true,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortTranslationDownStateDown(
                userId,
                true,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortTranslationDown(userId, true, search)
        }
    }

    data class Params(
        val sortParams: WordSort.Params,
        val searchPattern: String,
        val userId: Int?
    )
}
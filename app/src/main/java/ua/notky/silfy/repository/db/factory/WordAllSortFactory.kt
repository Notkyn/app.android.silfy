package ua.notky.silfy.repository.db.factory

import androidx.lifecycle.LiveData
import ua.notky.silfy.models.local.WordLocal
import ua.notky.silfy.models.states.SortLang
import ua.notky.silfy.models.states.SortState
import ua.notky.silfy.models.states.SortType
import ua.notky.silfy.repository.db.dao.word.WordAllSortDao
import ua.notky.silfy.tools.WordSort
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
            SortLang.UA_UP -> getByUaUp(
                params.sortParams.type,
                params.sortParams.state,
                params.searchPattern,
                params.userId
            )
            SortLang.UA_DOWN -> getByUaDown(
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

    private fun getByUaUp(
        type: SortType,
        state: SortState,
        search: String,
        userId: Int?
    ): LiveData<List<WordLocal>> {
        return when (type) {
            SortType.BLACKLIST -> getByUaUpTypeBlack(state, search, userId)
            SortType.FAVOURITE -> getByUaUpTypeFavourite(state, search, userId)
            SortType.DISABLE -> getByUaUpWithoutType(state, search, userId)
        }
    }

    private fun getByUaUpTypeBlack(
        state: SortState,
        search: String,
        userId: Int?
    ): LiveData<List<WordLocal>> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortUaUpStateUpBlack(
                userId,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortUaUpStateDownBlack(
                userId,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortUaUpBlack(
                userId,
                search
            )
        }
    }

    private fun getByUaUpTypeFavourite(
        state: SortState,
        search: String,
        userId: Int?
    ): LiveData<List<WordLocal>> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortUaUpStateUpFavourite(
                userId,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortUaUpStateDownFavourite(
                userId,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortUaUpFavourite(
                userId,
                search
            )
        }
    }

    private fun getByUaUpWithoutType(
        state: SortState,
        search: String,
        userId: Int?
    ): LiveData<List<WordLocal>> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortUaUpStateUp(
                userId,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortUaUpStateDown(
                userId,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortUaUp(userId, search)
        }
    }

    private fun getByUaDown(
        type: SortType,
        state: SortState,
        search: String,
        userId: Int?
    ): LiveData<List<WordLocal>> {
        return when (type) {
            SortType.BLACKLIST -> getByUaDownTypeBlack(state, search, userId)
            SortType.FAVOURITE -> getByUaDownTypeFavourite(state, search, userId)
            SortType.DISABLE -> getByUaDownWithoutType(state, search, userId)
        }
    }

    private fun getByUaDownTypeBlack(
        state: SortState,
        search: String,
        userId: Int?
    ): LiveData<List<WordLocal>> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortUaDownStateUpBlack(
                userId,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortUaDownStateDownBlack(
                userId,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortUaDownBlack(
                userId,
                search
            )
        }
    }

    private fun getByUaDownTypeFavourite(
        state: SortState,
        search: String,
        userId: Int?
    ): LiveData<List<WordLocal>> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortUaDownStateUpFavourite(
                userId,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortUaDownStateDownFavourite(
                userId,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortUaDownFavourite(
                userId,
                search
            )
        }
    }

    private fun getByUaDownWithoutType(
        state: SortState,
        search: String,
        userId: Int?
    ): LiveData<List<WordLocal>> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortUaDownStateUp(
                userId,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortUaDownStateDown(
                userId,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortUaDown(userId, search)
        }
    }

    data class Params(
        val sortParams: WordSort.Params,
        val searchPattern: String,
        val userId: Int?
    )
}
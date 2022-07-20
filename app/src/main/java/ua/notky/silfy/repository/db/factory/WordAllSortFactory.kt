package ua.notky.silfy.repository.db.factory

import ua.notky.silfy.models.local.WordDb
import ua.notky.silfy.models.states.SortLang
import ua.notky.silfy.models.states.SortState
import ua.notky.silfy.models.states.SortType
import ua.notky.silfy.repository.db.dao.WordAllSortDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import ua.notky.silfy.tools.WordSort
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 20.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class WordAllSortFactory @Inject constructor(
    private val sortDao: WordAllSortDao,
    private val dataStore: AppDataStorePreferences
) {

    suspend fun fetch(params: Params): List<WordDb> {
        return when (params.sortParams.lang) {
            SortLang.EN_UP -> getByEnUp(
                params.sortParams.type,
                params.sortParams.state,
                params.searchPattern
            )
            SortLang.EN_DOWN -> getByEnDown(
                params.sortParams.type,
                params.sortParams.state,
                params.searchPattern
            )
            SortLang.UA_UP -> getByUaUp(
                params.sortParams.type,
                params.sortParams.state,
                params.searchPattern
            )
            SortLang.UA_DOWN -> getByUaDown(
                params.sortParams.type,
                params.sortParams.state,
                params.searchPattern
            )
        }
    }

    private suspend fun getByEnUp(type: SortType, state: SortState, search: String): List<WordDb> {
        return when (type) {
            SortType.BLACKLIST -> getByEnUpTypeBlack(state, search)
            SortType.FAVOURITE -> getByEnUpTypeFavourite(state, search)
            SortType.DISABLE -> getByEnUpWithoutType(state, search)
        }
    }

    private suspend fun getByEnUpTypeBlack(state: SortState, search: String): List<WordDb> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortEnUpStateUpBlack(
                dataStore.getProfileId(),
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortEnUpStateDownBlack(
                dataStore.getProfileId(),
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortEnUpBlack(
                dataStore.getProfileId(),
                search
            )
        }
    }

    private suspend fun getByEnUpTypeFavourite(state: SortState, search: String): List<WordDb> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortEnUpStateUpFavourite(
                dataStore.getProfileId(),
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortEnUpStateDownFavourite(
                dataStore.getProfileId(),
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortEnUpFavourite(
                dataStore.getProfileId(),
                search
            )
        }
    }

    private suspend fun getByEnUpWithoutType(state: SortState, search: String): List<WordDb> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortEnUpStateUp(
                dataStore.getProfileId(),
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortEnUpStateDown(
                dataStore.getProfileId(),
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortEnUp(dataStore.getProfileId(), search)
        }
    }

    private suspend fun getByEnDown(
        type: SortType,
        state: SortState,
        search: String
    ): List<WordDb> {
        return when (type) {
            SortType.BLACKLIST -> getByEnDownTypeBlack(state, search)
            SortType.FAVOURITE -> getByEnDownTypeFavourite(state, search)
            SortType.DISABLE -> getByEnDownWithoutType(state, search)
        }
    }

    private suspend fun getByEnDownTypeBlack(state: SortState, search: String): List<WordDb> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortEnDownStateUpBlack(
                dataStore.getProfileId(),
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortEnDownStateDownBlack(
                dataStore.getProfileId(),
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortEnDownBlack(
                dataStore.getProfileId(),
                search
            )
        }
    }

    private suspend fun getByEnDownTypeFavourite(state: SortState, search: String): List<WordDb> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortEnDownStateUpFavourite(
                dataStore.getProfileId(),
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortEnDownStateDownFavourite(
                dataStore.getProfileId(),
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortEnDownFavourite(
                dataStore.getProfileId(),
                search
            )
        }
    }

    private suspend fun getByEnDownWithoutType(state: SortState, search: String): List<WordDb> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortEnDownStateUp(
                dataStore.getProfileId(),
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortEnDownStateDown(
                dataStore.getProfileId(),
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortEnDown(dataStore.getProfileId(), search)
        }
    }

    private suspend fun getByUaUp(type: SortType, state: SortState, search: String): List<WordDb> {
        return when (type) {
            SortType.BLACKLIST -> getByUaUpTypeBlack(state, search)
            SortType.FAVOURITE -> getByUaUpTypeFavourite(state, search)
            SortType.DISABLE -> getByUaUpWithoutType(state, search)
        }
    }

    private suspend fun getByUaUpTypeBlack(state: SortState, search: String): List<WordDb> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortUaUpStateUpBlack(
                dataStore.getProfileId(),
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortUaUpStateDownBlack(
                dataStore.getProfileId(),
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortUaUpBlack(
                dataStore.getProfileId(),
                search
            )
        }
    }

    private suspend fun getByUaUpTypeFavourite(state: SortState, search: String): List<WordDb> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortUaUpStateUpFavourite(
                dataStore.getProfileId(),
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortUaUpStateDownFavourite(
                dataStore.getProfileId(),
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortUaUpFavourite(
                dataStore.getProfileId(),
                search
            )
        }
    }

    private suspend fun getByUaUpWithoutType(state: SortState, search: String): List<WordDb> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortUaUpStateUp(
                dataStore.getProfileId(),
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortUaUpStateDown(
                dataStore.getProfileId(),
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortUaUp(dataStore.getProfileId(), search)
        }
    }

    private suspend fun getByUaDown(
        type: SortType,
        state: SortState,
        search: String
    ): List<WordDb> {
        return when (type) {
            SortType.BLACKLIST -> getByUaDownTypeBlack(state, search)
            SortType.FAVOURITE -> getByUaDownTypeFavourite(state, search)
            SortType.DISABLE -> getByUaDownWithoutType(state, search)
        }
    }

    private suspend fun getByUaDownTypeBlack(state: SortState, search: String): List<WordDb> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortUaDownStateUpBlack(
                dataStore.getProfileId(),
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortUaDownStateDownBlack(
                dataStore.getProfileId(),
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortUaDownBlack(
                dataStore.getProfileId(),
                search
            )
        }
    }

    private suspend fun getByUaDownTypeFavourite(state: SortState, search: String): List<WordDb> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortUaDownStateUpFavourite(
                dataStore.getProfileId(),
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortUaDownStateDownFavourite(
                dataStore.getProfileId(),
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortUaDownFavourite(
                dataStore.getProfileId(),
                search
            )
        }
    }

    private suspend fun getByUaDownWithoutType(state: SortState, search: String): List<WordDb> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortUaDownStateUp(
                dataStore.getProfileId(),
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortUaDownStateDown(
                dataStore.getProfileId(),
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortUaDown(dataStore.getProfileId(), search)
        }
    }

    data class Params(
        val sortParams: WordSort.Params,
        val searchPattern: String
    )
}
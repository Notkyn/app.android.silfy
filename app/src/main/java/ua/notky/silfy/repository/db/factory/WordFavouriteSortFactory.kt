package ua.notky.silfy.repository.db.factory

import ua.notky.silfy.models.local.WordData
import ua.notky.silfy.models.states.SortLang
import ua.notky.silfy.models.states.SortState
import ua.notky.silfy.models.states.SortType
import ua.notky.silfy.repository.db.dao.word.WordFavouriteSortDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import ua.notky.silfy.tools.WordSort
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 21.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class WordFavouriteSortFactory @Inject constructor(
    private val sortDao: WordFavouriteSortDao,
    private val dataStore: AppDataStorePreferences
) {

    suspend fun fetch(params: Params): List<WordData> {
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

    private suspend fun getByEnUp(type: SortType, state: SortState, search: String): List<WordData> {
        return when (type) {
            SortType.BLACKLIST -> getByEnUpTypeBlack(state, search)
            SortType.FAVOURITE -> getByEnUpTypeFavourite(state, search)
            SortType.DISABLE -> getByEnUpWithoutType(state, search)
        }
    }

    private suspend fun getByEnUpTypeBlack(state: SortState, search: String): List<WordData> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortEnUpStateUpBlack(
                dataStore.getProfileId(),
                true,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortEnUpStateDownBlack(
                dataStore.getProfileId(),
                true,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortEnUpBlack(
                dataStore.getProfileId(),
                true,
                search
            )
        }
    }

    private suspend fun getByEnUpTypeFavourite(state: SortState, search: String): List<WordData> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortEnUpStateUpFavourite(
                dataStore.getProfileId(),
                true,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortEnUpStateDownFavourite(
                dataStore.getProfileId(),
                true,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortEnUpFavourite(
                dataStore.getProfileId(),
                true,
                search
            )
        }
    }

    private suspend fun getByEnUpWithoutType(state: SortState, search: String): List<WordData> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortEnUpStateUp(
                dataStore.getProfileId(),
                true,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortEnUpStateDown(
                dataStore.getProfileId(),
                true,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortEnUp(dataStore.getProfileId(), true, search)
        }
    }

    private suspend fun getByEnDown(
        type: SortType,
        state: SortState,
        search: String
    ): List<WordData> {
        return when (type) {
            SortType.BLACKLIST -> getByEnDownTypeBlack(state, search)
            SortType.FAVOURITE -> getByEnDownTypeFavourite(state, search)
            SortType.DISABLE -> getByEnDownWithoutType(state, search)
        }
    }

    private suspend fun getByEnDownTypeBlack(state: SortState, search: String): List<WordData> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortEnDownStateUpBlack(
                dataStore.getProfileId(),
                true,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortEnDownStateDownBlack(
                dataStore.getProfileId(),
                true,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortEnDownBlack(
                dataStore.getProfileId(),
                true,
                search
            )
        }
    }

    private suspend fun getByEnDownTypeFavourite(state: SortState, search: String): List<WordData> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortEnDownStateUpFavourite(
                dataStore.getProfileId(),
                true,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortEnDownStateDownFavourite(
                dataStore.getProfileId(),
                true,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortEnDownFavourite(
                dataStore.getProfileId(),
                true,
                search
            )
        }
    }

    private suspend fun getByEnDownWithoutType(state: SortState, search: String): List<WordData> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortEnDownStateUp(
                dataStore.getProfileId(),
                true,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortEnDownStateDown(
                dataStore.getProfileId(),
                true,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortEnDown(dataStore.getProfileId(), true, search)
        }
    }

    private suspend fun getByUaUp(type: SortType, state: SortState, search: String): List<WordData> {
        return when (type) {
            SortType.BLACKLIST -> getByUaUpTypeBlack(state, search)
            SortType.FAVOURITE -> getByUaUpTypeFavourite(state, search)
            SortType.DISABLE -> getByUaUpWithoutType(state, search)
        }
    }

    private suspend fun getByUaUpTypeBlack(state: SortState, search: String): List<WordData> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortUaUpStateUpBlack(
                dataStore.getProfileId(),
                true,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortUaUpStateDownBlack(
                dataStore.getProfileId(),
                true,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortUaUpBlack(
                dataStore.getProfileId(),
                true,
                search
            )
        }
    }

    private suspend fun getByUaUpTypeFavourite(state: SortState, search: String): List<WordData> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortUaUpStateUpFavourite(
                dataStore.getProfileId(),
                true,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortUaUpStateDownFavourite(
                dataStore.getProfileId(),
                true,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortUaUpFavourite(
                dataStore.getProfileId(),
                true,
                search
            )
        }
    }

    private suspend fun getByUaUpWithoutType(state: SortState, search: String): List<WordData> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortUaUpStateUp(
                dataStore.getProfileId(),
                true,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortUaUpStateDown(
                dataStore.getProfileId(),
                true,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortUaUp(dataStore.getProfileId(), true, search)
        }
    }

    private suspend fun getByUaDown(
        type: SortType,
        state: SortState,
        search: String
    ): List<WordData> {
        return when (type) {
            SortType.BLACKLIST -> getByUaDownTypeBlack(state, search)
            SortType.FAVOURITE -> getByUaDownTypeFavourite(state, search)
            SortType.DISABLE -> getByUaDownWithoutType(state, search)
        }
    }

    private suspend fun getByUaDownTypeBlack(state: SortState, search: String): List<WordData> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortUaDownStateUpBlack(
                dataStore.getProfileId(),
                true,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortUaDownStateDownBlack(
                dataStore.getProfileId(),
                true,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortUaDownBlack(
                dataStore.getProfileId(),
                true,
                search
            )
        }
    }

    private suspend fun getByUaDownTypeFavourite(state: SortState, search: String): List<WordData> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortUaDownStateUpFavourite(
                dataStore.getProfileId(),
                true,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortUaDownStateDownFavourite(
                dataStore.getProfileId(),
                true,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortUaDownFavourite(
                dataStore.getProfileId(),
                true,
                search
            )
        }
    }

    private suspend fun getByUaDownWithoutType(state: SortState, search: String): List<WordData> {
        return when (state) {
            SortState.EXCELLENT -> sortDao.getAllBySortUaDownStateUp(
                dataStore.getProfileId(),
                true,
                search
            )
            SortState.UNKNOWN -> sortDao.getAllBySortUaDownStateDown(
                dataStore.getProfileId(),
                true,
                search
            )
            SortState.DISABLE -> sortDao.getAllBySortUaDown(dataStore.getProfileId(), true, search)
        }
    }

    data class Params(
        val sortParams: WordSort.Params,
        val searchPattern: String
    )
}
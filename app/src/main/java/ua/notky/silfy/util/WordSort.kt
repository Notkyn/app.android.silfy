package ua.notky.silfy.util

import ua.notky.silfy.models.model.Word
import ua.notky.silfy.models.states.SortLang
import ua.notky.silfy.models.states.SortState
import ua.notky.silfy.models.states.SortType
import ua.notky.silfy.models.states.WordState

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 05.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
object WordSort {

    fun sort(words: List<Word>, params: Params): List<Word> {
        // sorted: type -> state -> lang
        return when {
            params.type != SortType.DISABLE || params.state != SortState.DISABLE -> sortByType(
                words,
                params
            )
            else -> words.sortedWith(getComparatorByLang(params))
        }
    }

    private fun getComparatorByLang(params: Params): Comparator<Word> {
        return Comparator { o1, o2 ->
            when (params.lang) {
                SortLang.EN_DOWN -> o1.en.compareTo(o2.en)
                SortLang.EN_UP -> o2.en.compareTo(o1.en)
                SortLang.UA_DOWN -> o1.ua.compareTo(o2.ua)
                SortLang.UA_UP -> o2.ua.compareTo(o1.ua)
            }
        }
    }

    private fun sortByState(words: List<Word>, params: Params): List<Word> {
        val result = mutableListOf<Word>()

        when (params.state) {
            SortState.EXCELLENT -> {
                result.addAll(words.filter { it.state == WordState.EXCELLENT }
                    .sortedWith(getComparatorByLang(params)))
                result.addAll(words.filter { it.state == WordState.GOOD }
                    .sortedWith(getComparatorByLang(params)))
                result.addAll(words.filter { it.state == WordState.AVERAGE }
                    .sortedWith(getComparatorByLang(params)))
                result.addAll(words.filter { it.state == WordState.POOR }
                    .sortedWith(getComparatorByLang(params)))
                result.addAll(words.filter { it.state == WordState.UNKNOWN }
                    .sortedWith(getComparatorByLang(params)))
            }
            SortState.UNKNOWN -> {
                result.addAll(words.filter { it.state == WordState.UNKNOWN }
                    .sortedWith(getComparatorByLang(params)))
                result.addAll(words.filter { it.state == WordState.POOR }
                    .sortedWith(getComparatorByLang(params)))
                result.addAll(words.filter { it.state == WordState.AVERAGE }
                    .sortedWith(getComparatorByLang(params)))
                result.addAll(words.filter { it.state == WordState.GOOD }
                    .sortedWith(getComparatorByLang(params)))
                result.addAll(words.filter { it.state == WordState.EXCELLENT }
                    .sortedWith(getComparatorByLang(params)))
            }
            else -> result.addAll(words.sortedWith(getComparatorByLang(params)))
        }

        return result
    }

    private fun sortByType(words: List<Word>, params: Params): List<Word> {
        val result = mutableListOf<Word>()

        when (params.type) {
            SortType.BLACKLIST -> {
                result.addAll(sortByState(words.filter { it.isBlacklist }, params))
                result.addAll(sortByState(words.filter { !it.isBlacklist }, params))
            }
            SortType.FAVOURITE -> {
                result.addAll(sortByState(words.filter { it.isFavourite }, params))
                result.addAll(sortByState(words.filter { !it.isFavourite }, params))
            }
            else -> result.addAll(sortByState(words, params))
        }

        return result
    }

    data class Params(
        val lang: SortLang = SortLang.EN_DOWN,
        val type: SortType = SortType.DISABLE,
        val state: SortState = SortState.DISABLE
    )
}
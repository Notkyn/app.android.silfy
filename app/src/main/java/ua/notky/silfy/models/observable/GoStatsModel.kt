package ua.notky.silfy.models.observable

import ua.notky.silfy.models.model.Word

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 11.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

data class GoStatsModel(
    var totalCountWords: Int = 0,
    private var countSuccess: Int = 0,
    private var countErrors: Int = 0,
    private val usedWords: MutableSet<Word> = mutableSetOf()
) {
    fun addUsedWord(word: Word) {
        usedWords.add(word)
    }

    fun getCountUsedWords() = usedWords.size

    fun addSuccess(isSuccess: Boolean) {
        if (isSuccess) {
            countSuccess++
        } else {
            countErrors++
        }
    }

    fun getCountSuccess() = countSuccess

    fun getCountErrors() = countErrors
}
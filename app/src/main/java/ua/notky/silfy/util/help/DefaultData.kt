package ua.notky.silfy.util.help

import ua.notky.silfy.models.states.WordState
import ua.notky.silfy.models.model.Word
import kotlin.random.Random

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 22.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

fun getTempAllWords(): List<Word> {
    val words = mutableListOf<Word>()

    for(i in 1 until 21) {
        words.add(
            Word(i,
                "EN_$i",
                "RU_$i",
                WordState.values()[Random.nextInt(0, 5)],
                Random.nextBoolean(),
                Random.nextBoolean()
                )
        )
    }

    return words
}

fun getTempFavouritesWords(): List<Word> {
    val words = mutableListOf<Word>()

    for(i in 1 until 11) {
        words.add(
            Word(i,
                "EN_$i",
                "RU_$i",
                WordState.values()[Random.nextInt(0, 5)],
                true,
                Random.nextBoolean()
            )
        )
    }

    return words
}

fun getTempBlacklistWords(): List<Word> {
    val words = mutableListOf<Word>()

    for(i in 1 until 7) {
        words.add(
            Word(i,
                "EN_$i",
                "RU_$i",
                WordState.values()[Random.nextInt(0, 5)],
                Random.nextBoolean(),
                true
            )
        )
    }

    return words
}
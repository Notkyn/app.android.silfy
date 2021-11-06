package ua.notky.silfy.util.help

import ua.notky.silfy.models.model.Category
import ua.notky.silfy.models.model.Word
import ua.notky.silfy.models.states.WordState
import kotlin.random.Random

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 22.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

fun getTempAllWords(): List<Word> {
    val words = mutableListOf<Word>()

    for(i in 0 until Random.nextInt(0, 20)) {
        if(i != 0) {
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
    }

    return words
}

fun getTempFavouritesWords(): List<Word> {
    val words = mutableListOf<Word>()

    for(i in 0 until Random.nextInt(0, 20)) {
        if(i != 0) {
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
    }

    return words
}

fun getTempBlacklistWords(): List<Word> {
    val words = mutableListOf<Word>()

    for(i in 0 until Random.nextInt(0, 20)) {
        if(i != 0) {
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
    }

    return words
}

fun getTempCategories(): List<Category> {
    val categories = mutableListOf<Category>()

    for(i in 0 until Random.nextInt(0, 20)) {
        if(i != 0) {
            categories.add(
                Category(
                    i,
                    "category_$i",
                    getTempAllWords()
                )
            )
        }
    }

    return categories
}
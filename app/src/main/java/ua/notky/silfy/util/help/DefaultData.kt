package ua.notky.silfy.util.help

import ua.notky.silfy.models.model.Category
import ua.notky.silfy.models.model.DictionaryInfo
import ua.notky.silfy.models.model.Profile
import ua.notky.silfy.models.model.Word
import ua.notky.silfy.models.states.WordState
import kotlin.random.Random

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 22.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@Deprecated("temp data")
fun getTempAllWords(): List<Word> {
    val words = mutableListOf<Word>()

    for (i in 0 until Random.nextInt(0, 20)) {
        if (i != 0) {
            words.add(
                Word(
                    i,
                    "EN_${Random.nextInt(100)}",
                    "RU_${Random.nextInt(100)}",
                    WordState.values()[Random.nextInt(0, 5)],
                    Random.nextBoolean(),
                    Random.nextBoolean()
                )
            )
        }
    }

    return words
}

@Deprecated("temp data")
fun getTempFavouritesWords(): List<Word> {
    val words = mutableListOf<Word>()

    for (i in 0 until Random.nextInt(0, 20)) {
        if (i != 0) {
            words.add(
                Word(
                    i,
                    "EN_${Random.nextInt(100)}",
                    "RU_${Random.nextInt(100)}",
                    WordState.values()[Random.nextInt(0, 5)],
                    true,
                    Random.nextBoolean()
                )
            )
        }
    }

    return words
}

@Deprecated("temp data")
fun getTempBlacklistWords(): List<Word> {
    val words = mutableListOf<Word>()

    for (i in 0 until Random.nextInt(0, 20)) {
        if (i != 0) {
            words.add(
                Word(
                    i,
                    "EN_${Random.nextInt(100)}",
                    "RU_${Random.nextInt(100)}",
                    WordState.values()[Random.nextInt(0, 5)],
                    Random.nextBoolean(),
                    true
                )
            )
        }
    }

    return words
}

@Deprecated("temp data")
fun getTempCategories(seed: Int = 50): List<Category> {
    val categories = mutableListOf<Category>()

    for (i in 0 until Random.nextInt(0, seed)) {
        if (i != 0) {
            categories.add(getTempCategory(i))
        }
    }

    return categories
}

@Deprecated("temp data")
fun getTempCategory(id: Int): Category {
    return Category(
        id,
        "category_${Random.nextInt(100)}",
        getTempAllWords()
    )
}

@Deprecated("temp data")
fun getTempCategory(name: String): Category {
    return Category(
        Random.nextInt(1000, 2000),
        name,
        getTempAllWords()
    )
}

@Deprecated("temp data")
fun getTempProfile(): Profile {
    return Profile(
        11,
        "First Name",
        "Last Name",
        "",
        "test_email@gmail.com",
        System.currentTimeMillis()
    )
}

@Deprecated("temp data")
fun getTempProfiles(): List<Profile> {
    val list: MutableList<Profile> = mutableListOf()

    for (i in 0..Random.nextInt(15)) {
        list.add(
            Profile(
                i,
                "First Name - $i",
                "Last Name - $i",
                "",
                "test_email_$i@gmail.com",
                System.currentTimeMillis() - (1000 * Random.nextInt(100))
            )
        )
    }

    return list
}

@Deprecated("temp data")
fun getTempDictionaryInfo(): DictionaryInfo {
    return DictionaryInfo(
        Random.nextInt(1000, 5000),
        Random.nextInt(100, 500),
        Random.nextInt(0, 5)
    )
}
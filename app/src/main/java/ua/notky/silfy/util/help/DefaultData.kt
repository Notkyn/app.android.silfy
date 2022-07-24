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
    val words = mutableListOf(
        Word(
            1,
            "Apple",
            "Яблуко",
            WordState.values()[Random.nextInt(0, 5)],
            100,
            Random.nextBoolean(),
            Random.nextBoolean()
        ),
        Word(
            2,
            "One",
            "Один",
            WordState.values()[Random.nextInt(0, 5)],
            100,
            Random.nextBoolean(),
            Random.nextBoolean()
        ),
        Word(
            3,
            "Key",
            "Ключ",
            WordState.values()[Random.nextInt(0, 5)],
            100,
            Random.nextBoolean(),
            Random.nextBoolean()
        ),
        Word(
            4,
            "Window",
            "Вікно",
            WordState.values()[Random.nextInt(0, 5)],
            100,
            Random.nextBoolean(),
            Random.nextBoolean()
        ),
        Word(
            5,
            "Little",
            "Маленький",
            WordState.values()[Random.nextInt(0, 5)],
            100,
            Random.nextBoolean(),
            Random.nextBoolean()
        ),
        Word(
            6,
            "Cat",
            "Кіт",
            WordState.values()[Random.nextInt(0, 5)],
            100,
            Random.nextBoolean(),
            Random.nextBoolean()
        ),
        Word(
            7,
            "Tree",
            "Дерево",
            WordState.values()[Random.nextInt(0, 5)],
            100,
            Random.nextBoolean(),
            Random.nextBoolean()
        ),
        Word(
            8,
            "Country",
            "Країна",
            WordState.values()[Random.nextInt(0, 5)],
            100,
            Random.nextBoolean(),
            Random.nextBoolean()
        ),
        Word(
            9,
            "House",
            "Дім",
            WordState.values()[Random.nextInt(0, 5)],
            100,
            Random.nextBoolean(),
            Random.nextBoolean()
        ),
        Word(
            10,
            "Good",
            "Добре",
            WordState.values()[Random.nextInt(0, 5)],
            100,
            Random.nextBoolean(),
            Random.nextBoolean()
        )
    )

    return words
}

@Deprecated("temp data")
fun getTempFavouritesWords(): List<Word> {
    val words = mutableListOf(
        Word(
            7,
            "Tree",
            "Дерево",
            WordState.values()[Random.nextInt(0, 5)],
            100,
            true,
            Random.nextBoolean()
        ),
        Word(
            8,
            "Country",
            "Країна",
            WordState.values()[Random.nextInt(0, 5)],
            100,
            true,
            Random.nextBoolean()
        ),
        Word(
            9,
            "House",
            "Дім",
            WordState.values()[Random.nextInt(0, 5)],
            100,
            true,
            Random.nextBoolean()
        )
    )

    return words
}

@Deprecated("temp data")
fun getTempBlacklistWords(): List<Word> {
    val words = mutableListOf(
        Word(
            4,
            "Window",
            "Вікно",
            WordState.values()[Random.nextInt(0, 5)],
            100,
            Random.nextBoolean(),
            Random.nextBoolean()
        ),
        Word(
            5,
            "Little",
            "Маленький",
            WordState.values()[Random.nextInt(0, 5)],
            100,
            Random.nextBoolean(),
            Random.nextBoolean()
        ),
        Word(
            6,
            "Cat",
            "Кіт",
            WordState.values()[Random.nextInt(0, 5)],
            100,
            Random.nextBoolean(),
            Random.nextBoolean()
        ),
        Word(
            7,
            "Tree",
            "Дерево",
            WordState.values()[Random.nextInt(0, 5)],
            100,
            Random.nextBoolean(),
            Random.nextBoolean()
        )
    )

    return words
}

@Deprecated("temp data")
fun getTempCategories(seed: Int = 50): List<Category> {
    val categories = mutableListOf(
        Category(
            1,
            "Рослини",
            getTempBlacklistWords()
        ),
        Category(
            2,
            "Звірі",
            getTempFavouritesWords()
        ),
        Category(
            3,
            "Мистецтво",
            getTempAllWords()
        )
    )

    return categories
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
fun getTempDictionaryInfo(): DictionaryInfo {
    return DictionaryInfo(
        Random.nextInt(1000, 5000),
        Random.nextInt(100, 500),
        Random.nextInt(0, 5)
    )
}
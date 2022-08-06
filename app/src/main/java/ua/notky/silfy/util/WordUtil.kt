package ua.notky.silfy.util

import android.content.Context
import ua.notky.base.extension.readJsonFile
import ua.notky.base.util.fromJsonToObjects
import ua.notky.silfy.models.dto.CategoryDto
import ua.notky.silfy.models.dto.WordDto

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

private const val FILE_NAME_WORDS = "words/words"
private const val FILE_EXTENSION_WORDS = ".json"
private const val A_Z = "abcdefghijklmnopqrstuvwyz"
private const val FILE_NAME_CATEGORIES = "categories.json"

fun Context.getWordsFromAssets(): List<WordDto> {
    val result: MutableList<WordDto> = mutableListOf()

    for(index in A_Z.indices) {
        val fileName = "${FILE_NAME_WORDS}_${A_Z[index]}$FILE_EXTENSION_WORDS"
        val jsonData = this.assets.readJsonFile(fileName)
        jsonData.fromJsonToObjects<WordDto>()?.let { words ->
            result.addAll(words)
        }
    }

    return result
}

fun Context.getCategoriesFromAssets(): List<CategoryDto>? {
    val jsonData = this.assets.readJsonFile(FILE_NAME_CATEGORIES)
    return jsonData.fromJsonToObjects()
}
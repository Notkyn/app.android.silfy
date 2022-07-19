package ua.notky.silfy.util

import android.content.Context
import ua.notky.base.extension.readJsonFile
import ua.notky.base.util.fromJsonToObjects
import ua.notky.silfy.models.dto.WordDto

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

private const val FILE_NAME_WORDS = "words.json"

fun Context.getWordsFromAssets(): List<WordDto>? {
    val jsonData = this.assets.readJsonFile(FILE_NAME_WORDS)
    return jsonData.fromJsonToObjects()
}
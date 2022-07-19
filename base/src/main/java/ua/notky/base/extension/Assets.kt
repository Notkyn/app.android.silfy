package ua.notky.base.extension

import android.content.res.AssetManager

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

fun AssetManager.readJsonFile(fileName: String): String {
    return this.open(fileName)
        .bufferedReader()
        .use { it.readText() }
}
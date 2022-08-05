package ua.notky.content.usecase

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import ua.notky.content.model.RawWord
import java.io.File
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 05.08.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class FetchRawWordsUseCase @Inject constructor(
    @ApplicationContext val context: Context
) {

    fun fetch(onResult: (Result<Unit>) -> Unit) {
        val runnable = Runnable {
            try {
                context.readRawFromAsset()
                    .formatData()
                    .forEach {
                        it.value.writeToFile(context.createFile(it.key))
                    }

                onResult.invoke(Result.success(Unit))
            } catch (ex: Exception) {
                ex.printStackTrace()
                onResult.invoke(Result.failure(ex))
            }
        }

        Thread(runnable).start()
    }

    private fun Context.readRawFromAsset(): List<String> {
        return this.assets.open(FILE_WORDS_TXT)
            .bufferedReader()
            .use {
                it.readLines()
            }
    }

    private fun List<String>.formatData(): Map<String, List<RawWord>> {
        return this.map { text ->

            if(text.contains("[")) {
                val indexFirst = text.indexOfFirst { it == '[' }
                val splitCategories = text.substring(indexFirst)
                    .replace("[", "")
                    .split("]")

                val listCategories: MutableList<String> = mutableListOf()

                splitCategories.forEach {
                    if(it.trim().isNotEmpty()) {
                        listCategories.add(it.trim())
                    }
                }

                val splitWords = text.substring(0, indexFirst).lowercase().split(" - ")

                RawWord(
                    splitWords.first().trim(),
                    splitWords.last().trim(),
                    listCategories
                )
            } else {
                val splitWords = text.lowercase().split(" - ")
                RawWord(
                    splitWords.first().trim(),
                    splitWords.last().trim(),
                    listOf()
                )
            }
        }.distinct().groupBy {
            it.en[0].toString()
        }
    }

    private fun List<RawWord>.writeToFile(file: File) {
        val writer = file.bufferedWriter()

        writer.write("[")

        this.forEachIndexed { index, word ->
            writer.newLine()
            writer.write("\t{")
            writer.newLine()

            writer.write("\t\t\"$KEY_WORDS_EN\": \"${word.en}\",")
            writer.newLine()
            writer.write("\t\t\"$KEY_WORDS_UA\": \"${word.ua}\",")
            writer.newLine()

            if(word.categories.isEmpty()) {
                writer.write("\t\t\"$KEY_WORDS_CATEGORIES\": []")
                writer.newLine()
            } else {
                writer.write("\t\t\"$KEY_WORDS_CATEGORIES\": [")
                writer.newLine()

                word.categories.forEachIndexed { position, item ->
                    if(position != word.categories.size - 1) {
                        writer.write("\t\t\t\"$item\",")
                        writer.newLine()
                    } else {
                        writer.write("\t\t\t\"$item\"")
                        writer.newLine()
                        writer.write("\t\t]")
                        writer.newLine()
                    }
                }
            }

            if (index != this.size - 1) {
                writer.write("\t},")
            } else {
                writer.write("\t}")
                writer.newLine()
            }
        }

        writer.write("]")
        writer.flush()
        writer.close()
    }

    private fun Context.createFile(prefix: String): File {
        val dirName = "${this.filesDir}${DIR_PART_PATH}"
        val fileNameTemp = "${FILE_WORDS_JSON}_$prefix${FILE_EXTENSION}"

        val myDir = File(dirName)
        myDir.mkdirs()

        val file = File(myDir, fileNameTemp)

        if (file.exists()) file.delete()

        return file
    }

    companion object {
        private const val FILE_WORDS_TXT = "words.txt"
        private const val FILE_WORDS_JSON = "words"

        private const val KEY_WORDS_EN = "en"
        private const val KEY_WORDS_UA = "ua"
        private const val KEY_WORDS_CATEGORIES = "categories"

        private const val DIR_PART_PATH = "/raw_data/words"
        private const val FILE_EXTENSION = ".json"
    }
}
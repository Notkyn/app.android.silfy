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
                val rawWords = context.readRawFromAsset()

                context.writeRawWithSort(rawWords)

                val words = rawWords.formatData()

                words.forEach {
                    it.value.writeToFile(context.createFile(it.key))
                }

                context.writeToLogFile(words, rawWords)

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

            if (text.contains("[")) {
                val indexFirst = text.indexOfFirst { it == '[' }
                val splitCategories = text.substring(indexFirst)
                    .replace("[", "")
                    .split("]")

                val listCategories: MutableList<String> = mutableListOf()

                splitCategories.forEach {
                    if (it.trim().isNotEmpty()) {
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

            if (word.categories.isEmpty()) {
                writer.write("\t\t\"$KEY_WORDS_CATEGORIES\": []")
                writer.newLine()
            } else {
                writer.write("\t\t\"$KEY_WORDS_CATEGORIES\": [")
                writer.newLine()

                word.categories.forEachIndexed { position, item ->
                    if (position != word.categories.size - 1) {
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

    private fun Context.writeToLogFile(words: Map<String, List<RawWord>>, rawWords: List<String>) {
        // create file log
        val dirName = "${this.filesDir}${DIR_PATH_LOGS}"

        val myDir = File(dirName)
        myDir.mkdirs()

        val file = File(myDir, FILE_WORDS_LOGS)

        if (file.exists()) file.delete()

        val writer = file.bufferedWriter()

        // count all words
        val countWords = words.keys.sumOf { words[it]?.size ?: 0 }

        writer.write("Count All Words: $countWords")
        writer.newLine()
        writer.write("Count Raw Words: ${rawWords.size}")
        writer.newLine()
        writer.newLine()

        // find distinct words
        val distinctWords: MutableList<String> = mutableListOf()
        words.keys.map {
            val listWords = words[it]?.groupBy { word -> word.en }

            distinctWords.addAll(
                listWords?.filterValues { result -> result.size > 1 }
                    ?.keys
                    ?.map { key ->
                        "$it - ${listWords[key]?.firstOrNull()?.en} - " +
                                "${listWords[key]?.size} - ${listWords[key].toString()}"
                    } ?: listOf()
            )
        }

        words.keys.forEach {
            writer.write("${it.uppercase()} - ${words[it]?.size}")
            writer.newLine()
        }

        writer.newLine()

        writer.write("Distinct words: ${distinctWords.size}")
        writer.newLine()
        distinctWords.forEach {
            writer.write(it)
            writer.newLine()
        }

        // find wrong words
        writer.newLine()
        writer.write("Wrong words: ")
        writer.newLine()

        words.mapValues {
            it.value.filter { rawWord ->
                rawWord.en == rawWord.ua
                        || rawWord.ua.contains(".")
                        || rawWord.ua.trim().last() == ','

            }
        }
            .filterValues { tempWords -> tempWords.isNotEmpty() }
            .forEach { (key, values) ->
                values.forEach { word ->
                    writer.write("${key.uppercase()} - ${word.en} - [${word.ua}]")
                    writer.newLine()
                }
            }

        // check raw words
        writer.newLine()
        writer.write("Check raw words: ")
        writer.newLine()

        rawWords.map { it.lowercase() }.groupBy { it[0] }.forEach { (key, values) ->
            if (values.size != words[key.toString()]?.size) {
                writer.write(
                    "${key.uppercase()} - " +
                            "[raw:${values.size}], [current:${words[key.toString()]?.size}]"
                )
                writer.newLine()
            }
        }

        rawWords.mapNotNull { text ->
            val enWord = text.lowercase().split(" - ").firstOrNull()?.trim()
            if (enWord.isNullOrEmpty()) {
                writer.write("NULL - $text")
            }
            enWord
        }.groupBy { it }
            .filterValues { it.size > 1 }
            .forEach { (key, values) ->
                writer.write("$key - $values")
                writer.newLine()
            }

        writer.flush()
        writer.close()
    }

    private fun Context.writeRawWithSort(words: List<String>) {
        // create file log
        val dirName = "${this.filesDir}${DIR_PART_PATH_RAW}"

        val myDir = File(dirName)
        myDir.mkdirs()

        val file = File(myDir, FILE_RAW_WORDS)

        if (file.exists()) file.delete()

        // write to file

        val writer = file.bufferedWriter()

        words.map { it.trim() }.sortedBy { it.lowercase() }.forEach {
            writer.write(it)
            writer.newLine()
        }

        writer.flush()
        writer.close()
    }

    companion object {
        private const val FILE_WORDS_TXT = "words.txt"
        private const val FILE_WORDS_JSON = "words"
        private const val FILE_WORDS_LOGS = "words_logs.txt"
        private const val FILE_RAW_WORDS = "words_raw.txt"

        private const val KEY_WORDS_EN = "en"
        private const val KEY_WORDS_UA = "ua"
        private const val KEY_WORDS_CATEGORIES = "categories"

        private const val DIR_PART_PATH = "/raw_data/words"
        private const val DIR_PATH_LOGS = "/raw_data/logs"
        private const val DIR_PART_PATH_RAW = "/raw_data/raw"
        private const val FILE_EXTENSION = ".json"
    }
}
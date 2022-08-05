package ua.notky.content.usecase

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 05.08.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class FetchRawCategoriesUseCase @Inject constructor(
    @ApplicationContext val context: Context
) {
    fun fetch(onResult: (Result<Unit>) -> Unit) {
        val runnable = Runnable {
            try {
                context.readRawFromAsset()
                    .formatData()
                    .distinct()
                    .writeToFile(context.createFile())

                onResult.invoke(Result.success(Unit))
            } catch (ex: Exception) {
                ex.printStackTrace()
                onResult.invoke(Result.failure(ex))
            }
        }

        Thread(runnable).start()
    }

    private fun Context.readRawFromAsset(): List<String> {
        return this.assets.open(FILE_CATEGORIES_TXT)
            .bufferedReader()
            .use {
                it.readLines()
            }
    }

    private fun List<String>.formatData(): List<String> {
        return this.map { text ->
            val title = text.replace("[", "")
                .replace("]", "")
                .trim()

            "\"$KEY_CATEGORY_TITLE\": \"$title\""
        }
    }

    private fun Context.createFile(): File {
        val dirName = "${this.filesDir}${DIR_PART_PATH}"
        val fileNameTemp = "${FILE_CATEGORIES_JSON}${FILE_EXTENSION}"

        val myDir = File(dirName)
        myDir.mkdirs()

        val file = File(myDir, fileNameTemp)

        if (file.exists()) file.delete()

        return file
    }

    private fun List<String>.writeToFile(file: File) {
        val writer = file.bufferedWriter()

        writer.write("[")

        this.forEachIndexed { index, text ->
            writer.newLine()
            writer.write("\t{")
            writer.newLine()
            writer.write("\t\t$text")
            writer.newLine()
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

    companion object {
        private const val FILE_CATEGORIES_TXT = "categories.txt"
        private const val FILE_CATEGORIES_JSON = "categories"
        private const val KEY_CATEGORY_TITLE = "title"

        private const val DIR_PART_PATH = "/raw_data"
        private const val FILE_EXTENSION = ".json"
    }
}
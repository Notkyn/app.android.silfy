package ua.notky.silfy.tools.image

import android.content.Context
import android.net.Uri
import java.io.*

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

fun Uri.toFileBuffered(context: Context, fileName: String): File {
    val inStream = context.contentResolver.openInputStream(this)

    val tempFile = File(context.cacheDir, fileName)
    tempFile.createNewFile()
    var outStream: FileOutputStream? = null
    val bufferedOutStream: BufferedInputStream?
    val baf = ByteArray(BUFFER_SIZE)
    try {
        outStream = FileOutputStream(tempFile)
        bufferedOutStream = BufferedInputStream(inStream, BUFFER_SIZE)
        var actual = 0
        while (actual != -1) {
            outStream.write(baf, 0, actual)
            actual = bufferedOutStream.read(baf, 0, BUFFER_SIZE)
        }
    } catch (fileEx: FileNotFoundException) {
        fileEx.printStackTrace()
    } catch (ioEx: IOException) {
        ioEx.printStackTrace()
    } finally {
        outStream?.close()
    }

    return tempFile
}

const val BUFFER_SIZE = 23 * 1024
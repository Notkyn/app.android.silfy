package ua.notky.silfy.tools.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.util.Size
import java.io.*
import kotlin.math.roundToInt

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

const val BUFFER_SIZE = 23 * 1024
const val SIZE_LARGE_IMAGE_FOR_DECODE: Int = 250

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

fun Uri.getScaledImage(context: Context): Bitmap {
    val contentResolver = context.contentResolver
    val imageSource = ImageDecoder.createSource(contentResolver, this)
    return ImageDecoder.decodeBitmap(imageSource) { decoder, imageInfo, _ ->
        val newSize = getRequiredSize(imageInfo.size)
        decoder.setTargetSize(newSize.width, newSize.height)
    }
}

private fun getRequiredSize(originalSize: Size): Size {
    val reqHeight: Int
    val reqWidth: Int

    val originalHeight = originalSize.height
    val originalWidth = originalSize.width
    val isLandscapeImage = originalWidth > originalHeight

    if (isLandscapeImage) {
        val heightScaleRatio = SIZE_LARGE_IMAGE_FOR_DECODE / originalHeight.toFloat()
        reqHeight = SIZE_LARGE_IMAGE_FOR_DECODE
        reqWidth = (originalWidth * heightScaleRatio).roundToInt()
    } else {
        val widthScaleRatio = SIZE_LARGE_IMAGE_FOR_DECODE / originalWidth.toFloat()
        reqHeight = (originalHeight * widthScaleRatio).roundToInt()
        reqWidth = SIZE_LARGE_IMAGE_FOR_DECODE
    }

    return Size(reqWidth, reqHeight)
}
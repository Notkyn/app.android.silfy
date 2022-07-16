package ua.notky.silfy.tools.image

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

fun decodeSampledBitmapFromResource(
    path: String?,
    reqWidth: Int,
    reqHeight: Int
): Bitmap? {

    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeFile(path, options)

    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

    options.inJustDecodeBounds = false
    BitmapFactory.decodeFile(path, options)

    options.inJustDecodeBounds = false
    return BitmapFactory.decodeFile(path, options)
}

private fun calculateInSampleSize(
    options: BitmapFactory.Options,
    reqWidth: Int,
    reqHeight: Int
): Int {

    val height = options.outHeight
    val width = options.outWidth
    var inSampleSize = 1
    if (height > reqHeight || width > reqWidth) {
        val halfHeight = height / 2
        val halfWidth = width / 2

        while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
            inSampleSize *= 2
        }
    }
    return inSampleSize
}

fun Bitmap.saveToFile(dirName: String, fileName: String): File {
    val myDir = File(dirName)
    myDir.mkdirs()

    val file = File(myDir, fileName)

    if (file.exists()) file.delete()

    val out = FileOutputStream(file)
    this.compress(Bitmap.CompressFormat.JPEG, 70, out)
    out.flush()
    out.close()

    return file
}
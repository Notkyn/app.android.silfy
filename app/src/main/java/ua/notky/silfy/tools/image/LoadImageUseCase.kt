package ua.notky.silfy.tools.image

import android.content.Context
import android.net.Uri

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
object LoadImageUseCase {
    private const val NAME_TEMP_FILE = "temp_file"
    private const val SIZE_IMAGE_FOR_DECODE: Int = 100
    private const val SIZE_LARGE_IMAGE_FOR_DECODE: Int = 250

    fun loadImage(
        context: Context,
        uri: Uri?,
        size: Int = SIZE_LARGE_IMAGE_FOR_DECODE
    ): LoadImageState {
        return if (uri == null) {
            LoadImageState.EmptyUri
        } else {
            try {
                val file = uri.toFileBuffered(context, NAME_TEMP_FILE)
                val bitmap = decodeSampledBitmapFromResource(file.absolutePath, size, size)

                LoadImageState.Success(uri, bitmap)
            } catch (ex: Exception) {
                ex.printStackTrace()
                LoadImageState.Error(ex)
            }
        }
    }
}
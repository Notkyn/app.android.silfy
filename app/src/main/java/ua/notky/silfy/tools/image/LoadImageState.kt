package ua.notky.silfy.tools.image

import android.graphics.Bitmap
import android.net.Uri

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

sealed class LoadImageState {
    data class Success(val uri: Uri, val bitmap: Bitmap? = null) : LoadImageState()
    object EmptyUri : LoadImageState()
    data class Error(val error: Throwable) : LoadImageState()
}
package ua.notky.base.extension

import android.widget.ImageView
import androidx.core.content.ContextCompat

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

fun ImageView.setImageTint(color: Int) {
    this.imageTintList = ContextCompat.getColorStateList(this.context, color)
}
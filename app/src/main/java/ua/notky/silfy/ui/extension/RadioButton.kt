package ua.notky.silfy.ui.extension

import android.graphics.Typeface
import android.widget.RadioButton

/**
 * @project Silfy
 * @author Evgeniy Zarechnyi on 06.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

fun RadioButton.setTypeFaceWithCheckedListener() {
    this.setOnCheckedChangeListener { _, value ->
        val type = if (value) {
            Typeface.DEFAULT_BOLD
        } else {
            Typeface.DEFAULT
        }

        this.typeface = type
    }
}
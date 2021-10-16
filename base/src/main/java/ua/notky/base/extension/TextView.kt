package ua.notky.base.extension

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import timber.log.Timber

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

fun TextView.setColouredSpan(word: String, color: Int) {
    val spannableString = SpannableString(text)
    val start = text.indexOf(word)
    val end = text.indexOf(word) + word.length
    try {
        spannableString.setSpan(
            ForegroundColorSpan(color),
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        text = spannableString
    } catch (e: IndexOutOfBoundsException) {
        Timber.d("'$word' was not not found in TextView text")
    }
}
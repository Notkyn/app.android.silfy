package ua.notky.base.extension

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
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
    val end = text.indexOf(word) + word.length - 1
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

fun String.setBoldSpan(text: String?): Spannable {
    val spannableString = SpannableStringBuilder(this)
    if (text.isNullOrEmpty()) return spannableString
    val start: Int = this.indexOf(text)
    val end: Int = start + text.length
    return try {
        spannableString.setSpan(
            StyleSpan(Typeface.BOLD),
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString
    } catch (e: IndexOutOfBoundsException) {
        e.printStackTrace()
        throw IllegalStateException("'$text' was not not found in [$this]")
    }
}

fun String.setColouredSpan(text: String?, color: Int): String {
    if (text.isNullOrEmpty()) return this
    val spannableString = SpannableString(this)
    val start = this.indexOf(text)
    val end = start + text.length - 1
    return try {
        spannableString.setSpan(
            ForegroundColorSpan(color),
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.toString()
    } catch (e: IndexOutOfBoundsException) {
        e.printStackTrace()
        throw IllegalStateException("'$text' was not not found in [$this]")
    }
}
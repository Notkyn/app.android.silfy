package ua.notky.silfy.validation

import android.util.Patterns

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

fun checkEmailField(expect: String?): Boolean {
    return !expect.isNullOrEmpty() &&
            Patterns.EMAIL_ADDRESS.matcher(expect).matches()
}

fun checkWordEn(expect: String?): Boolean {
    val value = expect?.trim()

    return !value.isNullOrEmpty() &&
            value.matches(Regex("^[a-zA-Z ]+$"))
}

fun checkWordUa(expect: String?): Boolean {
    if(expect.isNullOrEmpty()) return false

    var result = true
    val list = expect.split(",")

    if(list.isEmpty()) return false

    list.forEach {
        val value = it.trim()

        if(!value.matches(Regex("^([А-Яа-яЁёЇїІіЄєҐґ ])+$")) ||
                !value.matches(Regex("^[^ыЫъЪ]+$"))) {
            result = false
        }
    }

    return result
}
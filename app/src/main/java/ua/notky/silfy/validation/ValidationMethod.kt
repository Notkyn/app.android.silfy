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
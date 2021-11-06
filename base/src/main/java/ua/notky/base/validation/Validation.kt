package ua.notky.base.validation

import com.google.android.material.textfield.TextInputLayout

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

fun TextInputLayout.setErrorMsg(msg: String) {
    this.isErrorEnabled = true
    this.error = msg
}

fun TextInputLayout.clearError() {
    this.error = null
    this.isErrorEnabled = false
}
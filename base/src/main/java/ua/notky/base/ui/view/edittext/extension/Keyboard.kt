package ua.notky.base.ui.view.edittext.extension

import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import ua.notky.base.extension.hideKeyboard

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

fun isEnterPressed(keyEvent: KeyEvent?): Boolean {
    return keyEvent?.keyCode == KeyEvent.KEYCODE_ENTER ||
            keyEvent?.keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER
}

fun isDonePressed(key: Int?): Boolean {
    return key == EditorInfo.IME_ACTION_DONE
}

fun isNextPressed(key: Int?): Boolean {
    return key == EditorInfo.IME_ACTION_NEXT
}

fun View.setHideKeyboardWrapperListener() {
    this.setOnFocusChangeListener { view, hasFocus ->
        if(hasFocus) {
            view.hideKeyboard()
        }
    }
}
package ua.notky.base.ui.view.edittext.extension

import ua.notky.base.ui.view.edittext.ClearFocusEditText
import ua.notky.base.ui.view.edittext.listener.OnKeyActionDoneListener
import ua.notky.base.ui.view.edittext.listener.OnKeyBackPressedListener

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

fun ClearFocusEditText.doOnBackPressed(block: () -> Unit) {
    this.setOnBackPressedListener(object : OnKeyBackPressedListener {
        override fun onBackPressed() {
            block.invoke()
        }
    })
}

fun ClearFocusEditText.doOnActionDone(action: () -> Unit) {
    this.setOnActionDoneListener(object : OnKeyActionDoneListener {
        override fun onActionDone() {
            action.invoke()
        }
    })
}

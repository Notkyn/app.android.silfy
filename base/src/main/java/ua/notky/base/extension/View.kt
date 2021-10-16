package ua.notky.base.extension

import android.view.View

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

fun View.showView() {
    visibility = View.VISIBLE
}

fun View.hideView() {
    visibility = View.INVISIBLE
}
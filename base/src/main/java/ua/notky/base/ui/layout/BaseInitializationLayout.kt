package ua.notky.base.ui.layout

import android.content.res.TypedArray
import android.util.AttributeSet

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 12.05.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
interface BaseInitializationLayout {
    fun initializeBinding()
    fun setStyleableValue(typedArray: TypedArray) {}
    fun init(attrs: AttributeSet?) {}
    fun initializeViews() {}
    fun initializeListeners() {}
}
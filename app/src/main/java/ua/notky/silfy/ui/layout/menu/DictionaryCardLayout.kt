package ua.notky.silfy.ui.layout.menu

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.layout.frame.BaseBindingFrameLayout
import ua.notky.silfy.databinding.LayoutCardDictionaryBinding
import ua.notky.silfy.models.enums.DictionaryCardType

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 29.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class DictionaryCardLayout(context: Context, attrs: AttributeSet? = null) :
    BaseBindingFrameLayout<LayoutCardDictionaryBinding>(context, attrs) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> LayoutCardDictionaryBinding
        get() = LayoutCardDictionaryBinding::inflate

    fun setType(type: DictionaryCardType) {
        binding.type = type
    }

    fun setContent(value: Int) {
        binding.content = value
    }

    fun handleClick(action: () -> Unit) {
        binding.buttonAction.setOnClickListener { action.invoke() }
    }
}
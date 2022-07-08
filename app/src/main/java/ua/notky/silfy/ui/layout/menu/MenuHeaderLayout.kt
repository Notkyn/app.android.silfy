package ua.notky.silfy.ui.layout.menu

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.layout.constraint.BaseBindingConstraintLayout
import ua.notky.silfy.databinding.LayoutMenuHeaderBinding

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 26.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class MenuHeaderLayout(context: Context, attrs: AttributeSet? = null) :
    BaseBindingConstraintLayout<LayoutMenuHeaderBinding>(context, attrs) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> LayoutMenuHeaderBinding
        get() = LayoutMenuHeaderBinding::inflate

    fun setTitle(title: String) {
        binding.title = title
    }

    fun setGoMode(value: Boolean) {
        binding.goMode = value
    }

    fun handleBackClick(action: () -> Unit) {
        binding.buttonBack.setOnClickListener { action.invoke() }
    }

    fun handleCloseClick(action: () -> Unit) {
        binding.buttonClose.setOnClickListener { action.invoke() }
    }
}
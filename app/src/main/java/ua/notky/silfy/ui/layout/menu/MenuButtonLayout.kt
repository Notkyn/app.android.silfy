package ua.notky.silfy.ui.layout.menu

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.layout.constraint.BaseBindingConstraintLayout
import ua.notky.silfy.databinding.LayoutMenuButtonBinding

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class MenuButtonLayout(context: Context, attrs: AttributeSet? = null) :
    BaseBindingConstraintLayout<LayoutMenuButtonBinding>(context, attrs) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> LayoutMenuButtonBinding
        get() = LayoutMenuButtonBinding::inflate

    fun setTitle(title: String?) {
        binding.title = title ?: ""
    }

    fun handleClick(action: () -> Unit) {
        binding.container.setOnClickListener { action.invoke() }
    }
}
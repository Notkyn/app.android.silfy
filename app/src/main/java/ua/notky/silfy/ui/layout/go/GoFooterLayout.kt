package ua.notky.silfy.ui.layout.go

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.layout.constraint.BaseBindingConstraintLayout
import ua.notky.silfy.databinding.LayoutFooterGoBinding
import ua.notky.silfy.models.observable.GoModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 09.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class GoFooterLayout(context: Context, attrs: AttributeSet? = null) :
    BaseBindingConstraintLayout<LayoutFooterGoBinding>(context, attrs) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> LayoutFooterGoBinding
        get() = LayoutFooterGoBinding::inflate

    fun setModel(model: GoModel) {
        binding.model = model
    }

    fun handleNextClick(action: () -> Unit) {
        binding.buttonNext.setOnClickListener { action.invoke() }
    }
}
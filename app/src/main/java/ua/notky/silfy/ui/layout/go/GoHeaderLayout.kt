package ua.notky.silfy.ui.layout.go

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.layout.constraint.BaseBindingConstraintLayout
import ua.notky.silfy.databinding.LayoutHeaderGoBinding
import ua.notky.silfy.models.observable.GoModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 08.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class GoHeaderLayout(context: Context, attrs: AttributeSet? = null) :
    BaseBindingConstraintLayout<LayoutHeaderGoBinding>(context, attrs) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> LayoutHeaderGoBinding
        get() = LayoutHeaderGoBinding::inflate

    fun setModel(model: GoModel) {
        binding.model = model
    }

    fun handleCancelClick(action: () -> Unit) {
        binding.buttonClose.setOnClickListener { action.invoke() }
    }
}
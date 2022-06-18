package ua.notky.silfy.ui.layout.word

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.layout.constraint.BaseBindingConstraintLayout
import ua.notky.base.viewmodel.state.StateAttachModel
import ua.notky.silfy.databinding.LayoutHeaderWordEditBinding
import ua.notky.silfy.models.observable.StateModel

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 11.05.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class HeaderWordEditLayout(context: Context, attrs: AttributeSet? = null) :
    BaseBindingConstraintLayout<LayoutHeaderWordEditBinding>(context, attrs), StateAttachModel<StateModel> {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> LayoutHeaderWordEditBinding
        get() = LayoutHeaderWordEditBinding::inflate

    fun handleBackClick(action: () -> Unit) {
        binding.buttonBack.setOnClickListener { action.invoke() }
    }

    override fun setState(state: StateModel) {
        binding.state = state
    }
}
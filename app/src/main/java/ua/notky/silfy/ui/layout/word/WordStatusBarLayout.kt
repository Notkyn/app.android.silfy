package ua.notky.silfy.ui.layout.word

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.layout.constraint.BaseBindingConstraintLayout
import ua.notky.silfy.databinding.LayoutWordStatusBarBinding
import ua.notky.silfy.models.observable.WordsModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 18.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class WordStatusBarLayout(context: Context, attrs: AttributeSet? = null) :
    BaseBindingConstraintLayout<LayoutWordStatusBarBinding>(context, attrs) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> LayoutWordStatusBarBinding
        get() = LayoutWordStatusBarBinding::inflate

    fun setModel(model: WordsModel) {
        binding.model = model
    }

    fun handleWordStateClick(action: () -> Unit) {
        binding.buttonWordState.setOnClickListener {
            action.invoke()
        }

        binding.textWordState.setOnClickListener {
            action.invoke()
        }
    }
}
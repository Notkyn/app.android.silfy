package ua.notky.silfy.ui.layout.go

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.layout.liner.BaseBindingLinerLayout
import ua.notky.base.ui.view.edittext.extension.doOnActionDone
import ua.notky.silfy.databinding.LayoutGoAnswerWriteBinding
import ua.notky.silfy.models.observable.answer.WordAnswerWriteModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 10.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class GoWriteAnswerLayout(context: Context, attrs: AttributeSet? = null) :
    BaseBindingLinerLayout<LayoutGoAnswerWriteBinding>(context, attrs) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> LayoutGoAnswerWriteBinding
        get() = LayoutGoAnswerWriteBinding::inflate

    private var answerCallback: (() -> Unit)? = null

    override fun initializeViews() {
        binding.edit.setTargetForCleanFocus(binding.input)
    }

    override fun initializeListeners() {
        binding.button.setOnClickListener { answerCallback?.invoke() }
        binding.edit.doOnActionDone { answerCallback?.invoke() }
    }

    fun handleAnswer(action: () -> Unit) {
        answerCallback = action
    }

    fun setModel(model: WordAnswerWriteModel) {
        binding.model = model
    }
}
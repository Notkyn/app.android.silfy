package ua.notky.silfy.ui.layout.go

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.adapter.extensions.doOnItemClick
import ua.notky.base.ui.layout.frame.BaseBindingFrameLayout
import ua.notky.silfy.databinding.LayoutGoAnswerSelectBinding
import ua.notky.silfy.models.model.Word
import ua.notky.silfy.models.observable.WordAnswerModel
import ua.notky.silfy.ui.adapter.AnswerWordSelectorAdapter

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 10.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class GoSelectAnswerLayout(context: Context, attrs: AttributeSet? = null) :
    BaseBindingFrameLayout<LayoutGoAnswerSelectBinding>(context, attrs) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> LayoutGoAnswerSelectBinding
        get() = LayoutGoAnswerSelectBinding::inflate

    private lateinit var answerWordsAdapter: AnswerWordSelectorAdapter

    private var clickCallback: ((WordAnswerModel) -> Unit)? = null

    override fun initializeViews() {
        initializeAdapter()
    }

    private fun initializeAdapter() {
        answerWordsAdapter = AnswerWordSelectorAdapter()
        binding.recycler.adapter = answerWordsAdapter

        answerWordsAdapter.doOnItemClick { clickCallback?.invoke(it) }
    }

    fun handleClick(action: (WordAnswerModel) -> Unit) {
        clickCallback = action
    }

    fun setWords(words: List<WordAnswerModel>) {
        answerWordsAdapter.clearAndAddAll(words)
    }
}
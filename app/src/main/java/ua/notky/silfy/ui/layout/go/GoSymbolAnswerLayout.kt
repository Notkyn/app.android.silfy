package ua.notky.silfy.ui.layout.go

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.flexbox.*
import ua.notky.base.ui.adapter.extensions.doOnItemClick
import ua.notky.base.ui.layout.liner.BaseBindingLinerLayout
import ua.notky.silfy.databinding.LayoutGoAnswerSymbolBinding
import ua.notky.silfy.models.model.Word
import ua.notky.silfy.models.observable.SymbolModel
import ua.notky.silfy.models.observable.answer.WordAnswerSymbolModel
import ua.notky.silfy.ui.adapter.AnswerWordSymbolAdapter

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 10.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class GoSymbolAnswerLayout(context: Context, attrs: AttributeSet? = null) :
    BaseBindingLinerLayout<LayoutGoAnswerSymbolBinding>(context, attrs) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> LayoutGoAnswerSymbolBinding
        get() = LayoutGoAnswerSymbolBinding::inflate

    private lateinit var symbolAdapter: AnswerWordSymbolAdapter

    private var addSymbolCallback: ((String) -> Unit)? = null
    private var deleteSymbolCallback: ((String) -> Unit)? = null

    override fun initializeViews() {
        initializeAdapter()
    }

    private fun initializeAdapter() {
        symbolAdapter = AnswerWordSymbolAdapter()
        binding.recycler.adapter = symbolAdapter

        val layoutManager = FlexboxLayoutManager(context).apply {
            justifyContent = JustifyContent.CENTER
            alignItems = AlignItems.CENTER
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
        }

        binding.recycler.layoutManager = layoutManager

        symbolAdapter.doOnItemClick { onSelectItem(it) }
    }

    fun setModel(model: WordAnswerSymbolModel) {
        binding.model = model
    }

    fun handleAnswer(action: () -> Unit) {
        binding.button.setOnClickListener { action.invoke() }
    }

    fun setWord(word: Word) {
        binding.model?.langType?.get()?.let { type ->
            val value = word.getValueByType(type)

            val symbols: MutableList<String> = mutableListOf()

            for (element in value) {
                symbols.add(element.toString())
            }

            symbols.shuffle()

            symbolAdapter.clearAndAddAll(symbols.map { SymbolModel(it) })
        }
    }

    private fun onSelectItem(item: SymbolModel) {
        if (item.isSelect.get()) {
            deleteSymbolCallback?.invoke(item.symbol)
        } else {
            addSymbolCallback?.invoke(item.symbol)
        }

        item.isSelect.set(!item.isSelect.get())
    }

    fun handleAddSymbolClick(action: (String) -> Unit) {
        addSymbolCallback = action
    }

    fun handleDeleteSymbolClick(action: (String) -> Unit) {
        deleteSymbolCallback = action
    }
}
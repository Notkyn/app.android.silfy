package ua.notky.silfy.ui.binding.go

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import ua.notky.silfy.R
import ua.notky.silfy.models.enums.AnswerType
import ua.notky.silfy.models.enums.GoLangType
import ua.notky.silfy.models.enums.GoMode
import ua.notky.silfy.models.model.Word
import ua.notky.silfy.models.observable.answer.WordAnswerSymbolModel
import ua.notky.silfy.models.observable.answer.WordAnswerWriteModel
import ua.notky.silfy.ui.layout.go.GoSymbolAnswerLayout
import ua.notky.silfy.ui.layout.go.GoWriteAnswerLayout

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 10.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
object GoAnswerBindingAdapter {

    @JvmStatic
    @BindingAdapter("answerType")
    fun bindingAnswerType(view: Button, type: AnswerType?) {
        type?.let {
            when (it) {
                AnswerType.NORMAL -> {
                    view.isEnabled = true
                    view.backgroundTintList =
                        ContextCompat.getColorStateList(view.context, R.color.secondary_color)
                }
                AnswerType.DISABLE -> {
                    view.isEnabled = false
                }
                AnswerType.SUCCESS -> {
                    view.isEnabled = false
                    view.backgroundTintList =
                        ContextCompat.getColorStateList(view.context, R.color.button_answer_success)
                }
                AnswerType.ERROR -> {
                    view.isEnabled = false
                    view.backgroundTintList =
                        ContextCompat.getColorStateList(view.context, R.color.button_answer_error)
                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("onSelectGoMode")
    fun bindingOnSelectGoMode(view: View, mode: GoMode?) {
        mode?.let {
            val visible = when (it) {
                GoMode.SELECT -> View.VISIBLE
                else -> View.GONE
            }

            view.visibility = visible
        }
    }

    @JvmStatic
    @BindingAdapter("onWriteGoMode")
    fun bindingOnWriteGoMode(view: View, mode: GoMode?) {
        mode?.let {
            val visible = when (it) {
                GoMode.WRITE -> View.VISIBLE
                else -> View.GONE
            }

            view.visibility = visible
        }
    }

    @JvmStatic
    @BindingAdapter("onSymbolGoMode")
    fun bindingOnSymbolGoMode(view: View, mode: GoMode?) {
        mode?.let {
            val visible = when (it) {
                GoMode.SYMBOL -> View.VISIBLE
                else -> View.GONE
            }

            view.visibility = visible
        }
    }

    @JvmStatic
    @BindingAdapter("setActualWord", "setLangType")
    fun bindingSetActualWord(view: TextView, word: Word?, type: GoLangType?) {
        if (word != null && type != null) {
            view.text = word.getValueByType(type)
        }
    }

    @JvmStatic
    @BindingAdapter("setModel")
    fun bindingSetModel(view: GoWriteAnswerLayout, model: WordAnswerWriteModel?) {
        model?.let { view.setModel(it) }
    }

    @JvmStatic
    @BindingAdapter("setModel")
    fun bindingSetModel(view: GoSymbolAnswerLayout, model: WordAnswerSymbolModel?) {
        model?.let { view.setModel(it) }
    }

    @JvmStatic
    @BindingAdapter("setSelectSymbolState")
    fun bindingSetSelectSymbolState(view: Button, type: Boolean?) {
        if(type == true) {
            view.background = ContextCompat.getDrawable(view.context, R.drawable.bg_button_answer_symbol_selected)
            view.setTextColor(ContextCompat.getColorStateList(view.context, R.color.secondary_color))
        } else {
            view.background = ContextCompat.getDrawable(view.context, R.drawable.bg_button_answer_symbol_enable)
            view.setTextColor(ContextCompat.getColorStateList(view.context, R.color.text))
        }
    }
}
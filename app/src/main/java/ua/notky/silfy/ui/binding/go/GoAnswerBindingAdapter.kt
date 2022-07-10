package ua.notky.silfy.ui.binding.go

import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import ua.notky.silfy.R
import ua.notky.silfy.models.enums.AnswerType
import ua.notky.silfy.models.enums.GoMode

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
            val visible = when(it) {
                GoMode.SELECT -> View.VISIBLE
                else -> View.GONE
            }

            view.visibility = visible
        }
    }
}
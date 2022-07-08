package ua.notky.silfy.ui.binding.go

import android.widget.TextView
import androidx.databinding.BindingAdapter
import ua.notky.silfy.models.observable.GoModel
import ua.notky.silfy.ui.layout.go.GoHeaderLayout
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 08.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
object GoHeaderBindingAdapter {

    @JvmStatic
    @BindingAdapter("setTime")
    fun bindingSetTime(view: TextView, time: Long?) {
        val seconds = time ?: 0
        val formatter = DateTimeFormatter.ofPattern("mm:ss")
        val timeLocal = LocalTime.ofSecondOfDay(seconds)
        val text = formatter.format(timeLocal)
        view.text = text
    }

    @JvmStatic
    @BindingAdapter("setMaxErrors", "setCurrentErrors")
    fun bindingSetErrors(view: TextView, max: Int?, current: Int?) {
        val maxValue = max ?: 0
        val currentValue = current ?: 0

        val text = "$currentValue/$maxValue"
        view.text = text
    }

    @JvmStatic
    @BindingAdapter("setModel")
    fun bindingSetModel(view: GoHeaderLayout, model: GoModel?) {
        model?.let { view.setModel(it) }
    }
}
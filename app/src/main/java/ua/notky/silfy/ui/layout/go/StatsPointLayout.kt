package ua.notky.silfy.ui.layout.go

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import ua.notky.base.ui.layout.frame.BaseBindingFrameLayout
import ua.notky.silfy.databinding.LayoutStatsPointBinding
import ua.notky.silfy.models.enums.StatsPointType

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 11.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class StatsPointLayout(context: Context, attrs: AttributeSet? = null) :
    BaseBindingFrameLayout<LayoutStatsPointBinding>(context, attrs) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> LayoutStatsPointBinding
        get() = LayoutStatsPointBinding::inflate

    fun setType(type: StatsPointType) {
        binding.card.setCardBackgroundColor(ContextCompat.getColor(context, type.color))
        binding.title.text = context.getString(type.title)
    }

    fun setCounter(count: Int) {
        binding.counter.text = count.toString()
    }
}
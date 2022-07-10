package ua.notky.silfy.ui.binding.go

import androidx.databinding.BindingAdapter
import ua.notky.silfy.models.enums.StatsPointType
import ua.notky.silfy.ui.layout.go.StatsPointLayout

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 11.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
object StatsDialogBindingAdapter {

    @JvmStatic
    @BindingAdapter("setStatsPointType")
    fun bindingSetStatsPointType(view: StatsPointLayout, type: StatsPointType?) {
        type?.let { view.setType(it) }
    }

    @JvmStatic
    @BindingAdapter("setCounterStats")
    fun bindingSetCounterStats(view: StatsPointLayout, count: Int?) {
        count?.let { view.setCounter(it) }
    }
}
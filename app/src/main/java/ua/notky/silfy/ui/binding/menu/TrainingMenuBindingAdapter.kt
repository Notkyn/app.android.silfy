package ua.notky.silfy.ui.binding.menu

import android.widget.RadioGroup
import androidx.databinding.BindingAdapter
import ua.notky.silfy.R
import ua.notky.silfy.models.enums.DifficultType
import ua.notky.silfy.models.observable.TrainingSettingsModel
import ua.notky.silfy.ui.layout.menu.DifficultTrainingLayout

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 03.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
object TrainingMenuBindingAdapter {

    @JvmStatic
    @BindingAdapter("checkedDifficultButton")
    fun bindingCheckedDifficultButton(view: RadioGroup, type: DifficultType?) {
        type?.let {
            when(it) {
                DifficultType.EASY -> view.check(R.id.radio_easy)
                DifficultType.HARD -> view.check(R.id.radio_hard)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("setModel")
    fun bindingSetModel(view: DifficultTrainingLayout, model: TrainingSettingsModel?) {
        model?.let { view.setModel(it) }
    }
}
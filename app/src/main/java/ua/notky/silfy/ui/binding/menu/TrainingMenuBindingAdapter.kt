package ua.notky.silfy.ui.binding.menu

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import android.view.View
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import ua.notky.silfy.R
import ua.notky.silfy.models.enums.DifficultType
import ua.notky.silfy.models.enums.MenuHeaderType
import ua.notky.silfy.models.enums.SelectedWordsType
import ua.notky.silfy.models.enums.TrainingDurationType
import ua.notky.silfy.models.observable.TrainingSettingsModel
import ua.notky.silfy.ui.layout.menu.*


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
            when (it) {
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

    @JvmStatic
    @BindingAdapter("setModel")
    fun bindingSetModel(view: DurationTrainingLayout, model: TrainingSettingsModel?) {
        model?.let { view.setModel(it) }
    }

    @JvmStatic
    @BindingAdapter("setModel")
    fun bindingSetModel(view: CountErrorsTrainingLayout, model: TrainingSettingsModel?) {
        model?.let { view.setModel(it) }
    }

    @JvmStatic
    @BindingAdapter("setModel")
    fun bindingSetModel(view: SelectWordsTrainingLayout, model: TrainingSettingsModel?) {
        model?.let { view.setModel(it) }
    }

    @JvmStatic
    @BindingAdapter("setModel")
    fun bindingSetModel(view: CategoryTrainingLayout, model: TrainingSettingsModel?) {
        model?.let { view.setModel(it) }
    }

    @JvmStatic
    @BindingAdapter("setDurationFive")
    fun bindingDurationFive(view: TextView, type: TrainingDurationType?) {
        type?.let {
            setEnableDurationButton(view, it == TrainingDurationType.FIVE)
        }
    }

    @JvmStatic
    @BindingAdapter("setDurationTen")
    fun bindingDurationTen(view: TextView, type: TrainingDurationType?) {
        type?.let {
            setEnableDurationButton(view, it == TrainingDurationType.TEN)
        }
    }

    @JvmStatic
    @BindingAdapter("setDurationThirty")
    fun bindingDurationThirty(view: TextView, type: TrainingDurationType?) {
        type?.let {
            setEnableDurationButton(view, it == TrainingDurationType.THIRTY)
        }
    }

    @JvmStatic
    @BindingAdapter("setDurationInfinity")
    fun bindingDurationInfinity(view: TextView, type: TrainingDurationType?) {
        type?.let {
            setEnableDurationButton(view, it == TrainingDurationType.INFINITY)

            val icon = if (it == TrainingDurationType.INFINITY) {
                R.drawable.ic_infinity
            } else {
                R.drawable.ic_infinity_active
            }

            val imageSpan = ImageSpan(view.context, icon)

            val label: Spannable = SpannableString(" ")
            label.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            view.text = label
        }

    }

    private fun setEnableDurationButton(view: TextView, isEnable: Boolean) {
        if (isEnable) {
            view.setTextColor(
                ContextCompat.getColorStateList(
                    view.context,
                    R.color.secondary_color
                )
            )
            view.background =
                ContextCompat.getDrawable(view.context, R.drawable.bg_button_main_enable)
        } else {
            view.setTextColor(
                ContextCompat.getColorStateList(
                    view.context,
                    R.color.primary_dark_color
                )
            )
            view.background =
                ContextCompat.getDrawable(view.context, R.drawable.bg_button_revers_enable)
        }
    }

    @JvmStatic
    @BindingAdapter("checkedSelectWordsButton")
    fun bindingCheckedSelectWordsButton(view: RadioGroup, type: SelectedWordsType?) {
        type?.let {
            when (it) {
                SelectedWordsType.ALL -> view.check(R.id.radio_all)
                SelectedWordsType.FAVOURITE -> view.check(R.id.radio_favourites)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("setDisplayMode")
    fun bindingSetDisplayMode(view: Button, type: MenuHeaderType?) {
        type?.let {
            val visible = if(it == MenuHeaderType.GO) {
                View.VISIBLE
            } else {
                View.GONE
            }

            view.visibility = visible
        }
    }
}

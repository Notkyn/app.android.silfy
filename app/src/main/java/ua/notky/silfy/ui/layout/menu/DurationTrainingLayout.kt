package ua.notky.silfy.ui.layout.menu

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.layout.constraint.BaseBindingConstraintLayout
import ua.notky.silfy.databinding.LayoutTrainingDurationBinding
import ua.notky.silfy.models.enums.TrainingDurationType
import ua.notky.silfy.models.observable.TrainingSettingsModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 04.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class DurationTrainingLayout(context: Context, attrs: AttributeSet? = null) :
    BaseBindingConstraintLayout<LayoutTrainingDurationBinding>(context, attrs) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> LayoutTrainingDurationBinding
        get() = LayoutTrainingDurationBinding::inflate

    fun setModel(model: TrainingSettingsModel) {
        binding.model = model
    }

    fun handleDurationClick(action: (TrainingDurationType) -> Unit) {
        binding.button5.setOnClickListener { action.invoke(TrainingDurationType.FIVE) }
        binding.button10.setOnClickListener { action.invoke(TrainingDurationType.TEN) }
        binding.button30.setOnClickListener { action.invoke(TrainingDurationType.THIRTY) }
        binding.buttonInfinity.setOnClickListener { action.invoke(TrainingDurationType.INFINITY) }
    }
}
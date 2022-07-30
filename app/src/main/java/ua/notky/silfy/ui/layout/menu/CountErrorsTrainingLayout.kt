package ua.notky.silfy.ui.layout.menu

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.layout.constraint.BaseBindingConstraintLayout
import ua.notky.silfy.databinding.LayoutTrainingCountErrorsBinding
import ua.notky.silfy.models.observable.TrainingSettingsModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 04.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class CountErrorsTrainingLayout(context: Context, attrs: AttributeSet? = null) :
    BaseBindingConstraintLayout<LayoutTrainingCountErrorsBinding>(context, attrs) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> LayoutTrainingCountErrorsBinding
        get() = LayoutTrainingCountErrorsBinding::inflate

    fun setModel(model: TrainingSettingsModel) {
        binding.model = model
    }

    override fun initializeViews() {
        binding.edit.setTargetForCleanFocus(binding.divider)
    }

    fun handleFocusErrors(callback: (Boolean) -> Unit) {
        binding.edit.setOnFocusChangeListener { _, focus ->
            callback.invoke(focus)
        }
    }
}
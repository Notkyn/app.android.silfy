package ua.notky.silfy.ui.layout.menu

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import ua.notky.base.ui.layout.liner.BaseBindingLinerLayout
import ua.notky.silfy.databinding.LayoutTrainingPointDifficultBinding
import ua.notky.silfy.models.enums.DifficultType
import ua.notky.silfy.models.observable.TrainingSettingsModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 03.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class DifficultTrainingLayout(context: Context, attrs: AttributeSet? = null) :
    BaseBindingLinerLayout<LayoutTrainingPointDifficultBinding>(context, attrs) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> LayoutTrainingPointDifficultBinding
        get() = LayoutTrainingPointDifficultBinding::inflate

    fun setModel(model: TrainingSettingsModel) {
        binding.model = model
    }

    fun handleDifficult(action: (DifficultType) -> Unit) {
        binding.radioGroup.setOnCheckedChangeListener { _, id ->
            when (id) {
                binding.radioEasy.id -> action.invoke(DifficultType.EASY)
                binding.radioHard.id -> action.invoke(DifficultType.HARD)
            }
        }
    }

    override fun initializeListeners() {
        initializeCheckedRadioListener(binding.radioEasy)
        initializeCheckedRadioListener(binding.radioHard)
    }

    private fun initializeCheckedRadioListener(view: RadioButton) {
        view.setOnCheckedChangeListener { _, value ->
            val type = if (value) {
                Typeface.DEFAULT_BOLD
            } else {
                Typeface.DEFAULT
            }

            view.typeface = type
        }
    }
}
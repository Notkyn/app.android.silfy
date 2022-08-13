package ua.notky.silfy.ui.layout.menu

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.layout.constraint.BaseBindingConstraintLayout
import ua.notky.silfy.databinding.LayoutTrainingSelectWordsBinding
import ua.notky.silfy.extension.setTypeFaceWithCheckedListener
import ua.notky.silfy.models.enums.SelectedWordsType
import ua.notky.silfy.models.observable.TrainingSettingsModel

/**
 * @project Silfy
 * @author Evgeniy Zarechnyi on 06.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class SelectWordsTrainingLayout(context: Context, attrs: AttributeSet? = null) :
    BaseBindingConstraintLayout<LayoutTrainingSelectWordsBinding>(context, attrs) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> LayoutTrainingSelectWordsBinding
        get() = LayoutTrainingSelectWordsBinding::inflate

    fun setModel(model: TrainingSettingsModel) {
        binding.model = model
    }


    fun handleSelectWords(action: (SelectedWordsType) -> Unit) {
        binding.radioGroup.setOnCheckedChangeListener { _, id ->
            when (id) {
                binding.radioAll.id -> action.invoke(SelectedWordsType.ALL)
                binding.radioFavourites.id -> action.invoke(SelectedWordsType.FAVOURITE)
            }
        }
    }

    fun handleRefreshSelectedWords(action: () -> Unit) {
        binding.radioAll.setOnClickListener { action.invoke() }
        binding.radioFavourites.setOnClickListener { action.invoke() }
        binding.checkbox.setOnClickListener { action.invoke() }
    }

    override fun initializeListeners() {
        binding.radioAll.setTypeFaceWithCheckedListener()
        binding.radioFavourites.setTypeFaceWithCheckedListener()
    }
}
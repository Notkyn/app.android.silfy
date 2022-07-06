package ua.notky.silfy.ui.layout.menu

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.layout.liner.BaseBindingLinerLayout
import ua.notky.silfy.databinding.LayoutTrainingSelectWordsBinding
import ua.notky.silfy.models.enums.SelectedWordsType
import ua.notky.silfy.models.observable.TrainingSettingsModel
import ua.notky.silfy.ui.extension.setTypeFaceWithCheckedListener

/**
 * @project Silfy
 * @author Evgeniy Zarechnyi on 06.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class SelectWordsTrainingLayout(context: Context, attrs: AttributeSet? = null) :
    BaseBindingLinerLayout<LayoutTrainingSelectWordsBinding>(context, attrs) {

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

    override fun initializeListeners() {
        binding.radioAll.setTypeFaceWithCheckedListener()
        binding.radioFavourites.setTypeFaceWithCheckedListener()
    }
}
package ua.notky.silfy.ui.fragment.menu.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import ua.notky.base.extension.openSafePopBackstackScreen
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.databinding.FragmentMenuTrainingSettingsBinding
import ua.notky.silfy.viewmodel.menu.TrainingSettingsViewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 29.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class TrainingSettingsMenuFragment : BaseBindingFragment<FragmentMenuTrainingSettingsBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMenuTrainingSettingsBinding
        get() = FragmentMenuTrainingSettingsBinding::inflate

    private val trainingSettingsViewModel by viewModels<TrainingSettingsViewModel>()

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(trainingSettingsViewModel)
            .build()
    }

    override fun initializeViews() {
        binding.model = trainingSettingsViewModel.model
    }

    override fun initializeListeners() {
        binding.header.handleBackClick { openSafePopBackstackScreen() }

        binding.difficultLayout.handleDifficult(trainingSettingsViewModel::updateDifficult)

        binding.durationLayout.handleDurationClick(trainingSettingsViewModel::updateDuration)

        binding.selectWordsLayout.handleSelectWords(trainingSettingsViewModel::updateSelectWords)
    }

    override fun initializeData() {
        trainingSettingsViewModel.fetchData()
    }
}
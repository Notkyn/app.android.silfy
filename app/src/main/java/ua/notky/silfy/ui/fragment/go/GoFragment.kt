package ua.notky.silfy.ui.fragment.go

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.databinding.FragmentGoBinding
import ua.notky.silfy.viewmodel.go.GoViewModel
import ua.notky.silfy.viewmodel.menu.TrainingSettingsViewModel

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 08.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class GoFragment : BaseBindingFragment<FragmentGoBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentGoBinding
        get() = FragmentGoBinding::inflate

    private val goViewModel by activityViewModels<GoViewModel>()
    private val settingsViewModel by activityViewModels<TrainingSettingsViewModel>()

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(goViewModel)
            .build()
    }

    override fun initializeViews() {
        binding.model = goViewModel.model
    }

    override fun initializeViewModels() {
        goViewModel.isStarted = true
        goViewModel.initializeDifficult(settingsViewModel.model.difficult.get())
        goViewModel.initializeTimer(settingsViewModel.model.duration.get()?.seconds)
        goViewModel.initializeErrors(
            settingsViewModel.model.enableErrors.get(),
            settingsViewModel.model.countErrors.get()
        )
        goViewModel.initializeWords()
    }

    override fun initializeListeners() {
        binding.header.handleCancelClick { activity?.onBackPressed() }
        binding.footer.handleNextClick { goViewModel.onNext() }
    }
}
package ua.notky.silfy.ui.fragment.menu.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ua.notky.base.extension.observe
import ua.notky.base.extension.openSafePopBackstackScreen
import ua.notky.base.ui.dialog.exstensions.doOnConfirm
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.databinding.FragmentMenuTrainingSettingsBinding
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.ui.dialog.go.CancelTrainingBottomsheet
import ua.notky.silfy.ui.dialog.menu.CategoryTrainingBottomsheet
import ua.notky.silfy.viewmodel.menu.TrainingSettingsViewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 29.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class TrainingSettingsMenuFragment : BaseBindingFragment<FragmentMenuTrainingSettingsBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMenuTrainingSettingsBinding
        get() = FragmentMenuTrainingSettingsBinding::inflate

    private val trainingSettingsViewModel by activityViewModels<TrainingSettingsViewModel>()

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(trainingSettingsViewModel)
            .build()
    }

    override fun initializeViews() {
        binding.model = trainingSettingsViewModel.model
    }

    override fun initializeViewModels() {
        observe(trainingSettingsViewModel.categories, ::renderCategories)
    }

    override fun initializeListeners() {
        binding.header.handleBackClick { openSafePopBackstackScreen() }
        binding.header.handleCloseClick { showCancelTrainingDialog() }

        binding.difficultLayout.handleDifficult(trainingSettingsViewModel::updateDifficult)

        binding.durationLayout.handleDurationClick(trainingSettingsViewModel::updateDuration)

        binding.selectWordsLayout.handleSelectWords(trainingSettingsViewModel::updateSelectWords)

        binding.categoryLayout.handleDeleteClick(trainingSettingsViewModel::onDeleteCategory)
        binding.categoryLayout.handleAddClick(::showCategoryDialog)

        binding.buttonSave.setOnClickListener { trainingSettingsViewModel.onSave() }
        binding.buttonStart.setOnClickListener { /* todo */ }
    }

    override fun initializeData() {
        trainingSettingsViewModel.fetchData()
    }

    private fun renderCategories(categories: List<Category>?) {
        categories?.let {
            binding.categoryLayout.setCategories(it)
            trainingSettingsViewModel.checkChangedState()
        }
    }

    private fun showCategoryDialog() {
        val dialog = CategoryTrainingBottomsheet()

        dialog.show(parentFragmentManager, dialog::class.java.simpleName)
    }

    private fun showCancelTrainingDialog() {
        val dialog = CancelTrainingBottomsheet()

        dialog.doOnConfirm { activity?.finish() }

        dialog.show(parentFragmentManager, dialog::class.java.simpleName)
    }
}
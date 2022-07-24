package ua.notky.silfy.ui.dialog.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ua.notky.base.extension.observe
import ua.notky.base.extension.toast
import ua.notky.base.ui.dialog.bottomsheet.BaseBindingBottomSheetDialogFragment
import ua.notky.base.validation.ValidationError
import ua.notky.base.validation.clearError
import ua.notky.base.validation.setErrorMsg
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.R
import ua.notky.silfy.config.VALIDATION_CATEGORY_IS_EXIST
import ua.notky.silfy.config.VALIDATION_CATEGORY_NAME
import ua.notky.silfy.databinding.BottomsheetEditCategoryBinding
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.models.states.CategorySaveUiState
import ua.notky.silfy.viewmodel.category.CategoryViewModel
import ua.notky.silfy.viewmodel.category.EditCategoryViewModel

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 15.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class EditCategoryBottomsheet(private val category: Category? = null) :
    BaseBindingBottomSheetDialogFragment<BottomsheetEditCategoryBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BottomsheetEditCategoryBinding
        get() = BottomsheetEditCategoryBinding::inflate

    private val editCategoryViewModel by activityViewModels<EditCategoryViewModel>()
    private val categoryViewModel by activityViewModels<CategoryViewModel>()

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(editCategoryViewModel)
            .addValidationViewModel(editCategoryViewModel)
            .build()
    }

    override fun initialize(savedInstanceState: Bundle?) {
        editCategoryViewModel.onSelectCategory(category)
    }

    override fun initializeViews() {
        binding.model = editCategoryViewModel.model
        binding.editCategory.setTargetForCleanFocus(binding.divider)
    }

    override fun initializeListeners() {
        binding.buttonSave.setOnClickListener { onSaveCategory() }
    }

    override fun initializeViewModels() {
        observe(editCategoryViewModel.uiState, ::renderUiState)
    }

    private fun onSaveCategory() {
        editCategoryViewModel.onSaveCategory(categoryViewModel.getNamesAllCategories())
    }

    private fun renderUiState(state: CategorySaveUiState?) {
        when (state) {
            CategorySaveUiState.Saved -> {
                mOnConfirmListener?.onConfirm()
                dismiss()
            }
            CategorySaveUiState.Failure -> toast(R.string.error_saved_data)
            else -> {}
        }
    }

    override fun setValidationErrors(errors: List<ValidationError>) {
        errors.forEach {
            when (it.type) {
                VALIDATION_CATEGORY_NAME -> binding.inputCategory.setErrorMsg(it.msg)
                VALIDATION_CATEGORY_IS_EXIST -> binding.inputCategory.setErrorMsg(it.msg)
            }
        }
    }

    override fun clearValidationErrors() {
        binding.inputCategory.clearError()
    }
}
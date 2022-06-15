package ua.notky.silfy.ui.dialog.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ua.notky.base.extension.observe
import ua.notky.base.ui.dialog.bottomsheet.BaseBindingBottomSheetDialogFragment
import ua.notky.base.validation.ValidationError
import ua.notky.base.validation.clearError
import ua.notky.base.validation.setErrorMsg
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.config.VALIDATION_CATEGORY_IS_EXIST
import ua.notky.silfy.config.VALIDATION_CATEGORY_NAME
import ua.notky.silfy.databinding.BottomsheetNewCategoryBinding
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.viewmodel.category.CategoryViewModel
import ua.notky.silfy.viewmodel.category.NewCategoryViewModel

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 15.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class NewCategoryBottomsheet : BaseBindingBottomSheetDialogFragment<BottomsheetNewCategoryBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BottomsheetNewCategoryBinding
        get() = BottomsheetNewCategoryBinding::inflate

    private val newCategoryViewModel by activityViewModels<NewCategoryViewModel>()
    private val categoryViewModel by activityViewModels<CategoryViewModel>()

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(newCategoryViewModel)
            .addValidationViewModel(newCategoryViewModel)
            .build()
    }

    override fun initializeViews() {
        binding.model = newCategoryViewModel.model
        binding.editCategory.setTargetForCleanFocus(binding.divider)
    }

    override fun initializeListeners() {
        binding.buttonNew.setOnClickListener { clickNewCategory() }
    }

    override fun initializeViewModels() {
        observe(newCategoryViewModel.newCategory, ::renderNewCategory)
    }

    private fun clickNewCategory() {
        newCategoryViewModel.onCreateCategory(categoryViewModel.getNamesAllCategories())
    }

    private fun renderNewCategory(category: Category?) {
        category?.let {
            categoryViewModel.refreshCategories(it)
            newCategoryViewModel.clearData()
            dismiss()
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
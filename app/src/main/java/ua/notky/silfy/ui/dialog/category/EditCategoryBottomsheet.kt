package ua.notky.silfy.ui.dialog.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ua.notky.base.extension.hideKeyboard
import ua.notky.base.extension.observe
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.R
import ua.notky.silfy.databinding.BottomsheetEditCategoryBinding
import ua.notky.silfy.models.model.CategoryNameForm
import ua.notky.silfy.models.states.CategorySaveUiState
import ua.notky.silfy.ui.dialog.BaseSilfyBottomSheet
import ua.notky.silfy.ui.dialog.DialogTone
import ua.notky.silfy.ui.dialog.showSilfyDialog
import ua.notky.silfy.viewmodel.category.EditCategoryViewModel

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 15.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

/** 3c New / Edit category: opens with the keyboard, saves and closes; lists update by themselves (Room) */
@AndroidEntryPoint
class EditCategoryBottomsheet : BaseSilfyBottomSheet<BottomsheetEditCategoryBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BottomsheetEditCategoryBinding
        get() = BottomsheetEditCategoryBinding::inflate

    private val editCategoryViewModel by viewModels<EditCategoryViewModel>()

    /** The loaded name goes into the field once; after that the field is the source */
    private var isNameShown = false

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(editCategoryViewModel)
            .build()
    }

    override fun initialize(savedInstanceState: Bundle?) {
        isNameShown = savedInstanceState != null
        dialog?.window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        )
    }

    override fun initializeListeners() {
        binding.editName.doAfterTextChanged { editCategoryViewModel.setName(it?.toString().orEmpty()) }
        binding.editName.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) editCategoryViewModel.save()
            actionId == EditorInfo.IME_ACTION_DONE
        }
        binding.buttonSave.setOnClickListener { editCategoryViewModel.save() }
    }

    override fun initializeViewModels() {
        viewLifecycleOwner.observe(editCategoryViewModel.form, ::renderForm)
        viewLifecycleOwner.observe(editCategoryViewModel.uiState, ::renderUiState)
    }

    private fun renderForm(form: CategoryNameForm?) {
        form ?: return

        binding.textTitle.setText(if (form.isNew) R.string.categories_new else R.string.category_edit)

        if (!isNameShown) {
            isNameShown = true
            binding.editName.setText(form.name)
            binding.editName.setSelection(binding.editName.length())
            binding.editName.requestFocus()
        }

        binding.inputName.error = when (form.error) {
            CategoryNameForm.Error.EMPTY -> getString(R.string.category_error_empty)
            CategoryNameForm.Error.CHARS -> getString(R.string.category_error_chars)
            CategoryNameForm.Error.EXISTS -> getString(R.string.category_error_exists)
            null -> null
        }
    }

    private fun renderUiState(state: CategorySaveUiState?) {
        binding.buttonSave.isEnabled = state == CategorySaveUiState.Idle

        when (state) {
            CategorySaveUiState.Saved -> {
                binding.editName.hideKeyboard()
                dismiss()
            }
            CategorySaveUiState.Failure -> {
                editCategoryViewModel.consumeState()
                showSilfyDialog(
                    icon = R.drawable.ic_lc_triangle_alert,
                    tone = DialogTone.WARNING,
                    title = getString(R.string.category_error_save),
                    message = getString(R.string.create_error_message),
                    cancelText = null
                )
            }
            else -> {}
        }
    }

    companion object {
        private const val TAG = "EditCategoryBottomsheet"

        /** @param categoryId null — new category */
        fun show(fragmentManager: FragmentManager, categoryId: Int? = null) {
            if (fragmentManager.findFragmentByTag(TAG) != null) return
            EditCategoryBottomsheet().apply {
                arguments = bundleOf(
                    EditCategoryViewModel.ARG_CATEGORY_ID to (categoryId ?: EditCategoryViewModel.NEW_CATEGORY_ID)
                )
            }.show(fragmentManager, TAG)
        }
    }
}

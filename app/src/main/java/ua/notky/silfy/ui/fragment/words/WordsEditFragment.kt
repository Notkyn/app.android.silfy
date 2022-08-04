package ua.notky.silfy.ui.fragment.words

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ua.notky.base.extension.observe
import ua.notky.base.extension.openSafePopBackstackScreen
import ua.notky.base.extension.setBoldSpan
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.validation.ValidationError
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.R
import ua.notky.silfy.config.VALIDATION_WORD_EU
import ua.notky.silfy.config.VALIDATION_WORD_UA
import ua.notky.silfy.databinding.FragmentWordsEditBinding
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.models.states.EditWordUiState
import ua.notky.silfy.ui.dialog.category.SelectCategoryBottomsheet
import ua.notky.silfy.extension.showAlert
import ua.notky.silfy.extension.showSimpleAlert
import ua.notky.silfy.viewmodel.StateViewModel
import ua.notky.silfy.viewmodel.words.WordsEditViewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 23.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

class WordsEditFragment : BaseBindingFragment<FragmentWordsEditBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentWordsEditBinding
        get() = FragmentWordsEditBinding::inflate

    private val stateViewModel by activityViewModels<StateViewModel>()
    private val wordsEditViewModel by activityViewModels<WordsEditViewModel>()

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(wordsEditViewModel)
            .addValidationViewModel(wordsEditViewModel)
            .build()
    }


    override fun initializeViews() {
        binding.state = stateViewModel.state
        binding.model = wordsEditViewModel.model
        binding.wordModel = wordsEditViewModel.wordModel
        binding.translateModel = wordsEditViewModel.translateModel

        binding.formWord.setNextFocusTargetView(binding.formTranslate.getNextFocusTargetView())
        binding.formWord.setNextImeOptions()
        binding.formTranslate.setNextFocusTargetView(binding.formTranslate.getDoneFocusTargetView())
    }

    override fun initializeListeners() {
        binding.viewHeader.handleBackClick { openSafePopBackstackScreen() }

        binding.viewHeader.handleDeleteClick { showDeleteWordAlert() }

        binding.wordStatusBar.handleWordStateClick {
            wordsEditViewModel.onChangeWordState()
        }

        binding.buttonSave.setOnClickListener {
            wordsEditViewModel.onSaveWord()
        }

        binding.categories.handleDeleteClick {
            wordsEditViewModel.onDeleteCategoryFromWordList(it)
        }

        binding.categories.handleAddClick { showSelectCategoryDialog() }
    }

    override fun initializeViewModels() {
        observe(wordsEditViewModel.categories, ::renderCategories)
        observe(wordsEditViewModel.uiState, ::renderUiState)
    }

    private fun showDeleteWordAlert() {
        val title = getString(R.string.alert_title_word_delete)
            .format(wordsEditViewModel.wordModel.value.get())
            .setBoldSpan(wordsEditViewModel.wordModel.value.get())

        showAlert(
            title = title,
            onSuccess = { wordsEditViewModel.onDeleteWord() }
        )
    }

    private fun showSelectCategoryDialog() {
        val dialog = SelectCategoryBottomsheet(wordsEditViewModel.categories.value)

        dialog.show(parentFragmentManager, dialog::class.java.simpleName)
    }

    override fun setValidationErrors(errors: List<ValidationError>) {
        errors.forEach {
            when (it.type) {
                VALIDATION_WORD_EU -> binding.formWord.setError(it.msg)
                VALIDATION_WORD_UA -> binding.formTranslate.setError(it.msg)
                else -> {}
            }
        }
    }

    override fun clearValidationErrors() {
        binding.formWord.clearError()
        binding.formTranslate.clearError()
    }

    private fun renderCategories(categories: List<Category>?) {
        stateViewModel.updateEditable(wordsEditViewModel.isNewWord())
        wordsEditViewModel.checkChangedState()
        categories?.let { binding.categories.setCategories(it) }
    }

    private fun renderUiState(state: EditWordUiState?) {
        stateViewModel.setLoading(state == EditWordUiState.Deleting)

        when (state) {
            EditWordUiState.Failure.Load -> {
                showSimpleAlert(getString(R.string.alert_error_not_load_data))
                wordsEditViewModel.clearState()
            }
            EditWordUiState.Failure.Save -> {
                showSimpleAlert(getString(R.string.alert_error_saved_data))
                wordsEditViewModel.clearState()
            }
            EditWordUiState.Failure.Delete -> {
                showSimpleAlert(getString(R.string.alert_error_delete_data))
                wordsEditViewModel.clearState()
            }
            EditWordUiState.Deleted -> {
                wordsEditViewModel.clearState()
                openSafePopBackstackScreen()
            }
            EditWordUiState.Saved -> {
                wordsEditViewModel.clearState()
                openSafePopBackstackScreen()
            }
            else -> {}
        }
    }
}
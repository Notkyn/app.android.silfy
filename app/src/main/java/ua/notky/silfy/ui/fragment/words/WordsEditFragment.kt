package ua.notky.silfy.ui.fragment.words

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ua.notky.base.extension.observe
import ua.notky.base.extension.openSafePopBackstackScreen
import ua.notky.base.ui.adapter.extensions.doOnActionDelete
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.validation.ValidationError
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.config.ACTION_IS_SAVED
import ua.notky.silfy.config.VALIDATION_WORD_EU
import ua.notky.silfy.config.VALIDATION_WORD_UA
import ua.notky.silfy.databinding.FragmentWordsEditBinding
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.ui.adapter.CategoryInWordAdapter
import ua.notky.silfy.viewmodel.StateViewModel
import ua.notky.silfy.viewmodel.category.CategoryEditWordViewModel
import ua.notky.silfy.viewmodel.words.WordsEditViewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 23.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

class WordsEditFragment : BaseBindingFragment<FragmentWordsEditBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentWordsEditBinding
        get() = FragmentWordsEditBinding::inflate

    private val categoriesAdapter by lazy {
        return@lazy CategoryInWordAdapter()
    }

    private val stateViewModel by activityViewModels<StateViewModel>()
    private val wordsEditViewModel by activityViewModels<WordsEditViewModel>()
    private val categoryEditWordViewModel by activityViewModels<CategoryEditWordViewModel>()

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(wordsEditViewModel)
            .addViewModel(categoryEditWordViewModel)
            .addValidationViewModel(wordsEditViewModel)
            .build()
    }


    override fun initializeViews() {
        binding.state = stateViewModel.state
        binding.model = wordsEditViewModel.model
        binding.wordModel = wordsEditViewModel.wordModel
        binding.translateModel = wordsEditViewModel.translateModel

        binding.recyclerCategories.adapter = categoriesAdapter
    }

    override fun initializeListeners() {
        binding.viewHeader.handleBackClick { openSafePopBackstackScreen() }

        binding.buttonWordState.setOnClickListener {
            wordsEditViewModel.onChangeWordState()
        }

        binding.textWordState.setOnClickListener {
            wordsEditViewModel.onChangeWordState()
        }

        binding.buttonSave.setOnClickListener {
            wordsEditViewModel.onSaveWord()
        }

        categoriesAdapter.doOnActionDelete {
            categoryEditWordViewModel.onDeleteCategoryForEditWord(it)
        }
    }

    override fun initializeViewModels() {
        stateViewModel.updateEditable(wordsEditViewModel.isNewWord())

        categoryEditWordViewModel.onLoadCategoriesForEditWord(
            wordsEditViewModel.model.id
        )

        categoryEditWordViewModel.apply { observe(categoriesForEditWord, ::renderCategories) }
    }

    override fun handleActionVM(type: Int) {
        when (type) {
            ACTION_IS_SAVED -> openSafePopBackstackScreen()
        }
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
        categories?.let { categoriesAdapter.clearAndAddAll(it) }
    }
}
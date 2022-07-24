package ua.notky.silfy.ui.fragment.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import ua.notky.base.extension.observe
import ua.notky.base.extension.openSafePopBackstackScreen
import ua.notky.base.extension.openSafeScreen
import ua.notky.base.ui.adapter.extensions.doOnRootClick
import ua.notky.base.ui.dialog.exstensions.doOnConfirm
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.databinding.FragmentCategoryOverviewBinding
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.models.model.Word
import ua.notky.silfy.ui.adapter.WordAdapter
import ua.notky.silfy.ui.dialog.category.DeleteCategoryBottomsheet
import ua.notky.silfy.ui.dialog.category.EditCategoryBottomsheet
import ua.notky.silfy.util.WordSort
import ua.notky.silfy.viewmodel.StateViewModel
import ua.notky.silfy.viewmodel.category.CategoryOverviewViewModel
import ua.notky.silfy.viewmodel.words.WordsEditViewModel

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 15.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

class CategoryOverviewFragment : BaseBindingFragment<FragmentCategoryOverviewBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCategoryOverviewBinding
        get() = FragmentCategoryOverviewBinding::inflate

    private val categoryOverviewViewModel by activityViewModels<CategoryOverviewViewModel>()
    private val wordEditViewModel by activityViewModels<WordsEditViewModel>()
    private val stateViewModel by activityViewModels<StateViewModel>()

    private val wordAdapter: WordAdapter by lazy {
        return@lazy WordAdapter()
    }

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(categoryOverviewViewModel)
            .build()
    }

    override fun initializeViews() {
        binding.state = stateViewModel.state
        binding.model = categoryOverviewViewModel.model

        initializeRecycler()
    }

    private fun initializeRecycler() {
        binding.recycler.adapter = wordAdapter
        binding.recycler.itemAnimator = DefaultItemAnimator()

        wordAdapter.doOnRootClick { onNextEditWord(it) }
    }

    override fun initializeListeners() {
        initSortListeners()

        binding.header.handleBackClick { openSafePopBackstackScreen() }
        binding.info.handleEditClick { showEditCategoryDialog() }
        binding.info.handleDeleteClick { showDeleteDialog() }
    }

    private fun initSortListeners() {
        binding.sortView.handleSortEnClick {
            stateViewModel.setEnSort()
            categoryOverviewViewModel.onSortWords(binding.sortView.getSortParams())
        }
        binding.sortView.handleSortRuClick {
            stateViewModel.setRuSort()
            categoryOverviewViewModel.onSortWords(binding.sortView.getSortParams())
        }
        binding.sortView.handleSortTypeClick {
            stateViewModel.setTypeSort()
            categoryOverviewViewModel.onSortWords(binding.sortView.getSortParams())
        }
        binding.sortView.handleSortStateClick {
            stateViewModel.setStateSort()
            categoryOverviewViewModel.onSortWords(binding.sortView.getSortParams())
        }
    }

    override fun initializeViewModels() {
        observe(categoryOverviewViewModel.category, ::renderCategory)
        observe(categoryOverviewViewModel.words, ::renderWords)
    }

    private fun renderCategory(category: Category?) {
        category?.let {
            categoryOverviewViewModel.updateUiModel(it)
        }
    }

    private fun renderWords(words: List<Word>?) {
        words?.let {
            wordAdapter.clearAndAddAll(WordSort.sort(it, binding.sortView.getSortParams()))
        }
    }

    private fun showEditCategoryDialog() {
        val dialog = EditCategoryBottomsheet(categoryOverviewViewModel.getSelectedCategory())

        dialog.show(parentFragmentManager, dialog::class.java.simpleName)
    }

    private fun showDeleteDialog() {
        val dialog = DeleteCategoryBottomsheet(categoryOverviewViewModel.model.title.get())

        dialog.doOnConfirm { openSafePopBackstackScreen() }

        dialog.show(parentFragmentManager, dialog::class.java.simpleName)
    }

    private fun onNextEditWord(word: Word) {
        wordEditViewModel.selectWord(word)
        openSafeScreen(CategoryOverviewFragmentDirections.actionFragmentCategoryOverviewToFragmentWordsEdit())
    }
}
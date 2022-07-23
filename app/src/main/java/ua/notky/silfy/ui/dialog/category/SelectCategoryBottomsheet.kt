package ua.notky.silfy.ui.dialog.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import ua.notky.base.extension.observe
import ua.notky.base.ui.adapter.extensions.doOnRootClick
import ua.notky.base.ui.dialog.bottomsheet.BaseBindingBottomSheetDialogFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.databinding.BottomsheetSelectCategoryBinding
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.ui.adapter.SelectCategoryAdapter
import ua.notky.silfy.viewmodel.category.CategoryViewModel
import ua.notky.silfy.viewmodel.words.WordsEditViewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class SelectCategoryBottomsheet(
    val categories: List<Category>?
) : BaseBindingBottomSheetDialogFragment<BottomsheetSelectCategoryBinding>() {

    private val categoryViewModel by activityViewModels<CategoryViewModel>()
    private val wordsEditViewModel by activityViewModels<WordsEditViewModel>()

    private val categoryAdapter by lazy { return@lazy SelectCategoryAdapter() }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BottomsheetSelectCategoryBinding
        get() = BottomsheetSelectCategoryBinding::inflate

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(categoryViewModel)
            .addViewModel(wordsEditViewModel)
            .build()
    }

    override fun initializeViews() {
        binding.recyclerView.adapter = categoryAdapter
        binding.recyclerView.itemAnimator = DefaultItemAnimator()

        categoryAdapter.doOnRootClick {
            wordsEditViewModel.addCategory(it)
            dismiss()
        }
    }

    override fun initializeViewModels() {
        observe(categoryViewModel.categories, ::renderCategories)
    }

    override fun initializeData() {
        categoryViewModel.fetchData(categories)
    }

    private fun renderCategories(categories: List<Category>?) {
        categories?.let {
            renderEmptyView(it.isEmpty())
            categoryAdapter.clearAndAddAll(it)
        }
    }

    private fun renderEmptyView(isEmpty: Boolean) {
        if (isEmpty) {
            binding.recyclerView.visibility = View.GONE
            binding.textEmptyCategories.visibility = View.VISIBLE
        } else {
            binding.recyclerView.visibility = View.VISIBLE
            binding.textEmptyCategories.visibility = View.GONE
        }
    }
}
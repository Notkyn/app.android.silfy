package ua.notky.silfy.ui.fragment.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ua.notky.base.extension.observe
import ua.notky.base.extension.openSafeScreen
import ua.notky.base.ui.adapter.extensions.doOnRootClick
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.databinding.FragmentCategoryBinding
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.ui.adapter.CategoryAdapter
import ua.notky.silfy.ui.adapter.decorators.AddSpaceFirstItemDecorator
import ua.notky.silfy.ui.adapter.decorators.AddSpaceLastItemDecorator
import ua.notky.silfy.ui.dialog.category.EditCategoryBottomsheet
import ua.notky.silfy.viewmodel.StateViewModel
import ua.notky.silfy.viewmodel.category.CategoryOverviewViewModel
import ua.notky.silfy.viewmodel.category.CategoryViewModel
import ua.notky.silfy.viewmodel.category.EditCategoryViewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

class CategoryFragment : BaseBindingFragment<FragmentCategoryBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCategoryBinding
        get() = FragmentCategoryBinding::inflate

    private val categoryViewModel by activityViewModels<CategoryViewModel>()
    private val categoryOverviewViewModel by activityViewModels<CategoryOverviewViewModel>()
    private val editCategoryViewModel by activityViewModels<EditCategoryViewModel>()
    private val stateViewModel by activityViewModels<StateViewModel>()

    private val categoryAdapter by lazy { return@lazy CategoryAdapter() }

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(categoryViewModel)
            .addViewModel(categoryOverviewViewModel)
            .build()
    }

    override fun initializeViews() {
        binding.state = stateViewModel.state
        initializeAdapter()
    }

    private fun initializeAdapter() {
        binding.recycler.adapter = categoryAdapter
        binding.recycler.addItemDecoration(AddSpaceFirstItemDecorator(MARGIN_TOP_PX))
        binding.recycler.addItemDecoration(AddSpaceLastItemDecorator(MARGIN_BOTTOM_PX))

        categoryAdapter.doOnRootClick { onNextCategoryOverview(it) }
    }

    override fun initializeListeners() {
        binding.buttonNew.setOnClickListener { showNewCategoryDialog() }
    }

    override fun initializeViewModels() {
        observe(categoryViewModel.categories, ::renderCategories)
    }

    override fun initializeData() {
        stateViewModel.updatePresentValue()
        categoryViewModel.fetchData()
    }

    private fun renderCategories(categories: List<Category>?) {
        categories?.let {
            stateViewModel.updatePresentValue(it.isNotEmpty())
            categoryAdapter.clearAndAddAll(it)
        }
    }

    private fun showNewCategoryDialog() {
        editCategoryViewModel.clearState()

        val dialog = EditCategoryBottomsheet()

        dialog.show(parentFragmentManager, dialog::class.java.simpleName)
    }

    private fun onNextCategoryOverview(category: Category) {
        category.id?.let {
            stateViewModel.setDefaultSort()
            categoryOverviewViewModel.clearState()
            categoryOverviewViewModel.onSelectCategory(it)
            openSafeScreen(CategoryFragmentDirections.actionFragmentCategoryToFragmentCategoryOverview())
        }
    }

    companion object {
        private const val MARGIN_TOP_PX = 20
        private const val MARGIN_BOTTOM_PX = 350
    }
}
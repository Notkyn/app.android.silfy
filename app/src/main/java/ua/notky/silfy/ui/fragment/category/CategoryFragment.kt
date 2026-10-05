package ua.notky.silfy.ui.fragment.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ua.notky.base.extension.observe
import ua.notky.base.extension.openSafeScreen
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.R
import ua.notky.silfy.databinding.FragmentCategoryBinding
import ua.notky.silfy.models.model.CategorySummary
import ua.notky.silfy.ui.adapter.decorators.GridSpacingItemDecoration
import ua.notky.silfy.ui.dialog.category.EditCategoryBottomsheet
import ua.notky.silfy.ui.view.setLightSystemBars
import ua.notky.silfy.viewmodel.category.CategoriesViewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

/** 3a Categories: "{n} categories", 2-column grid with the dashed "New category" card */
@AndroidEntryPoint
class CategoryFragment : BaseBindingFragment<FragmentCategoryBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCategoryBinding
        get() = FragmentCategoryBinding::inflate

    private val categoriesViewModel by viewModels<CategoriesViewModel>()

    private val categoryAdapter = CategoryGridAdapter(
        onCategoryClick = { goToNextCategory(it) },
        onNewClick = { showNewCategorySheet() }
    )

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(categoriesViewModel)
            .build()
    }

    override fun onResume() {
        super.onResume()
        setLightSystemBars(true)
    }

    override fun initializeViews() {
        applyInsets()

        binding.recycler.adapter = categoryAdapter
        binding.recycler.itemAnimator = null
        binding.recycler.addItemDecoration(
            GridSpacingItemDecoration(resources.getDimensionPixelSize(R.dimen.ds_card_gap))
        )
    }

    override fun initializeListeners() {
        binding.buttonNew.setOnClickListener { showNewCategorySheet() }
    }

    override fun initializeViewModels() {
        viewLifecycleOwner.observe(categoriesViewModel.categories, ::renderCategories)
    }

    /** Status bar on top; the last row scrolls above the floating navigation */
    private fun applyInsets() {
        val initialTop = binding.root.paddingTop
        val initialBottom = binding.root.paddingBottom
        val navInset = resources.getDimensionPixelSize(R.dimen.ds_nav_content_inset)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(top = initialTop + bars.top, bottom = initialBottom + navInset + bars.bottom)
            insets
        }
        ViewCompat.requestApplyInsets(binding.root)
    }

    private fun renderCategories(categories: List<CategorySummary>?) {
        val list = categories.orEmpty()
        binding.textCount.text = resources.getQuantityString(R.plurals.plural_categories, list.size, list.size)
        categoryAdapter.submitCategories(list)
    }

    private fun showNewCategorySheet() {
        EditCategoryBottomsheet.show(childFragmentManager)
    }

    private fun goToNextCategory(category: CategorySummary) {
        openSafeScreen(
            CategoryFragmentDirections.actionFragmentCategoryToFragmentCategoryOverview(category.id)
        )
    }
}

package ua.notky.silfy.ui.layout.category

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import ua.notky.base.ui.adapter.extensions.doOnActionDelete
import ua.notky.base.ui.layout.constraint.BaseBindingConstraintLayout
import ua.notky.silfy.databinding.LayoutCategoryInfoForWordBinding
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.ui.adapter.CategoryInWordAdapter

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 18.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class CategoryInfoForWordLayout(context: Context, attrs: AttributeSet? = null) :
    BaseBindingConstraintLayout<LayoutCategoryInfoForWordBinding>(context, attrs) {

    private var categoriesAdapter: CategoryInWordAdapter? = null

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> LayoutCategoryInfoForWordBinding
        get() = LayoutCategoryInfoForWordBinding::inflate

    override fun initializeViews() {
        categoriesAdapter = CategoryInWordAdapter()
        binding.recyclerCategories.adapter = categoriesAdapter
        binding.recyclerCategories.itemAnimator = DefaultItemAnimator()
    }

    fun handleDeleteClick(action: (Category) -> Unit) {
        categoriesAdapter?.doOnActionDelete {
            action.invoke(it)
        }
    }

    fun handleAddClick(action: () -> Unit) {
        binding.buttonAddCategory.setOnClickListener { action.invoke() }
    }

    fun setCategories(categories: List<Category>?) {
        categories?.let {
            renderEmptyView(it.isEmpty())
            categoriesAdapter?.clearAndAddAll(it)
        }
    }

    private fun renderEmptyView(isEmpty: Boolean) {
        if(isEmpty) {
            binding.recyclerCategories.visibility = View.GONE
            binding.textEmptyCategories.visibility = View.VISIBLE
        } else {
            binding.recyclerCategories.visibility = View.VISIBLE
            binding.textEmptyCategories.visibility = View.GONE
        }
    }
}
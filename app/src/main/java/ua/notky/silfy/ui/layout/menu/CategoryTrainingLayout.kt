package ua.notky.silfy.ui.layout.menu

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import ua.notky.base.ui.adapter.extensions.doOnActionDelete
import ua.notky.base.ui.layout.liner.BaseBindingLinerLayout
import ua.notky.silfy.databinding.LayoutTrainingCategoryBinding
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.models.observable.TrainingSettingsModel
import ua.notky.silfy.ui.adapter.CategoryInWordAdapter

/**
 * @project Silfy
 * @author Evgeniy Zarechnyi on 07.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class CategoryTrainingLayout(context: Context, attrs: AttributeSet? = null) :
    BaseBindingLinerLayout<LayoutTrainingCategoryBinding>(context, attrs) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> LayoutTrainingCategoryBinding
        get() = LayoutTrainingCategoryBinding::inflate

    private var categoriesAdapter: CategoryInWordAdapter? = null

    fun setModel(model: TrainingSettingsModel) {
        binding.model = model
    }

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
        if (isEmpty) {
            binding.recyclerCategories.visibility = View.GONE
            binding.textEmptyCategories.visibility = View.VISIBLE
        } else {
            binding.recyclerCategories.visibility = View.VISIBLE
            binding.textEmptyCategories.visibility = View.GONE
        }
    }
}
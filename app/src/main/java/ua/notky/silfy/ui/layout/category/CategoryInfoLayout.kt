package ua.notky.silfy.ui.layout.category

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.layout.constraint.BaseBindingConstraintLayout
import ua.notky.silfy.databinding.LayoutCategoryInfoBinding
import ua.notky.silfy.models.observable.CategoryOverviewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 18.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class CategoryInfoLayout(context: Context, attrs: AttributeSet? = null) :
    BaseBindingConstraintLayout<LayoutCategoryInfoBinding>(context, attrs) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> LayoutCategoryInfoBinding
        get() = LayoutCategoryInfoBinding::inflate

    fun setModel(model: CategoryOverviewModel) {
        binding.model = model
    }

    fun handleEditClick(action: () -> Unit) {
        binding.imageEdit.setOnClickListener { action.invoke() }
    }

    fun handleDeleteClick(action: () -> Unit) {
        binding.imageDelete.setOnClickListener { action.invoke() }
    }
}
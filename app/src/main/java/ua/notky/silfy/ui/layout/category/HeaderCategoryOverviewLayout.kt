package ua.notky.silfy.ui.layout.category

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.layout.constraint.BaseBindingConstraintLayout
import ua.notky.silfy.databinding.LayoutHeaderCategoryOverviewBinding
import ua.notky.silfy.models.observable.CategoryOverviewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 18.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class HeaderCategoryOverviewLayout(context: Context, attrs: AttributeSet? = null) :
    BaseBindingConstraintLayout<LayoutHeaderCategoryOverviewBinding>(context, attrs) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> LayoutHeaderCategoryOverviewBinding
        get() = LayoutHeaderCategoryOverviewBinding::inflate

    fun handleBackClick(action: () -> Unit) {
        binding.buttonBack.setOnClickListener { action.invoke() }
    }

    fun setModel(model: CategoryOverviewModel) {
        binding.model = model
    }

}
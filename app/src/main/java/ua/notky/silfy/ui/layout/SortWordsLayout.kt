package ua.notky.silfy.ui.layout

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.layout.liner.BaseBindingLinerLayout
import ua.notky.base.viewmodel.state.StateAttachModel
import ua.notky.silfy.databinding.LayoutSortWordsBinding
import ua.notky.silfy.models.observable.StateModel

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 11.05.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class SortWordsLayout(context: Context, attrs: AttributeSet? = null) :
    BaseBindingLinerLayout<LayoutSortWordsBinding>(context, attrs), StateAttachModel<StateModel> {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> LayoutSortWordsBinding
        get() = LayoutSortWordsBinding::inflate

    override fun setState(state: StateModel) {
        binding.state = state
    }

    fun handleSortEnClick(action: () -> Unit) {
        binding.imageSortEn.setOnClickListener { action.invoke() }
    }

    fun handleSortRuClick(action: () -> Unit) {
        binding.imageSortRu.setOnClickListener { action.invoke() }
    }

    fun handleSortTypeClick(action: () -> Unit) {
        binding.imageSortType.setOnClickListener { action.invoke() }
    }

    fun handleSortStateClick(action: () -> Unit) {
        binding.imageSortState.setOnClickListener { action.invoke() }
    }
}
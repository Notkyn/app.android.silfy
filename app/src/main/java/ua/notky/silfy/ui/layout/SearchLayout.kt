package ua.notky.silfy.ui.layout

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import ua.notky.base.ui.layout.frame.BaseBindingFrameLayout
import ua.notky.base.util.toLog
import ua.notky.silfy.databinding.LayoutSearchBinding
import ua.notky.silfy.models.observable.StateModel

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 05.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class SearchLayout(context: Context, attrs: AttributeSet? = null) :
    BaseBindingFrameLayout<LayoutSearchBinding>(context, attrs) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> LayoutSearchBinding
        get() = LayoutSearchBinding::inflate

    override fun initializeViews() {
        binding.editSearch.setTargetForCleanFocus(binding.frameLayout)
    }

    fun setModel(model: StateModel) {
        binding.state = model
    }

    fun clearSearch() {
        binding.editSearch.clearFocus()
        binding.editSearch.setText("")
    }

    override fun initializeListeners() {
        binding.buttonClose.setOnClickListener {
            binding.editSearch.setText("")
        }
    }

    fun handleSearchPattern(callback: (String) -> Unit) {
        binding.editSearch.doOnTextChanged { text, _, _, _ ->
            text?.let {
                if(binding.editSearch.hasFocus()) {
                    callback.invoke(text.toString())
                }
            }
        }
    }

    fun getSearchPattern() = binding.editSearch.text?.toString() ?: ""
}
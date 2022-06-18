package ua.notky.silfy.ui.layout.word

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import ua.notky.base.ui.layout.liner.BaseBindingLinerLayout
import ua.notky.base.validation.clearError
import ua.notky.base.validation.setErrorMsg
import ua.notky.silfy.databinding.LayoutFormEnterWordBinding
import ua.notky.silfy.models.enums.WordFormType
import ua.notky.silfy.models.observable.FormWordModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 18.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class FormEnterWordLayout(context: Context, attrs: AttributeSet? = null) :
    BaseBindingLinerLayout<LayoutFormEnterWordBinding>(context, attrs) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> LayoutFormEnterWordBinding
        get() = LayoutFormEnterWordBinding::inflate

    override fun initializeViews() {
        binding.edit.setTargetForCleanFocus(binding.divider)
    }

    fun setType(type: WordFormType) {
        binding.type = type
    }

    fun setModel(model: FormWordModel) {
        binding.model = model
    }

    fun setError(msg: String) {
        binding.input.setErrorMsg(msg)
    }

    fun clearError() {
        binding.input.clearError()
    }

    fun setNextFocusTargetView(view: View) {
        binding.edit.setNextTargetView(view)
    }

    fun getNextFocusTargetView(): View {
        return binding.edit
    }

    fun setNextImeOptions() {
        binding.edit.imeOptions = EditorInfo.IME_ACTION_NEXT
    }
}
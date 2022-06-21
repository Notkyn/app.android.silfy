package ua.notky.silfy.ui.layout.profile

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.layout.liner.BaseBindingLinerLayout
import ua.notky.silfy.databinding.LayoutProfileInfoBinding
import ua.notky.silfy.models.enums.ProfileInfoType

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 21.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class ProfileInfoLayout(context: Context, attrs: AttributeSet? = null) :
    BaseBindingLinerLayout<LayoutProfileInfoBinding>(context, attrs) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> LayoutProfileInfoBinding
        get() = LayoutProfileInfoBinding::inflate

    fun setType(type: ProfileInfoType) {
        binding.type = type
    }

    fun setValue(value: Any) {
        binding.value = value
    }
}
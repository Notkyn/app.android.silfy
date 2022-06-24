package ua.notky.silfy.ui.layout.profile

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import ua.notky.base.ui.layout.constraint.BaseBindingConstraintLayout
import ua.notky.silfy.databinding.LayoutHeaderProfileBinding
import ua.notky.silfy.models.observable.ProfileModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class ProfileHeaderLayout(context: Context, attrs: AttributeSet? = null) :
    BaseBindingConstraintLayout<LayoutHeaderProfileBinding>(context, attrs) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> LayoutHeaderProfileBinding
        get() = LayoutHeaderProfileBinding::inflate

    fun setModel(model: ProfileModel) {
        binding.model = model
    }

    fun handleEditClick(action: () -> Unit) {
        binding.buttonEdit.setOnClickListener { action.invoke() }
    }

    fun handleEditPhotoClick(action: () -> Unit) {
        binding.imageAvatar.setOnClickListener { action.invoke() }
    }
}
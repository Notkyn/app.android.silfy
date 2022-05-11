package ua.notky.base.ui.layout.constraint

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import java.lang.ref.WeakReference

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 11.05.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
abstract class BaseBindingConstraintLayout<VDB : ViewDataBinding> @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : BaseConstraintLayout(context, attrs) {

    private lateinit var _binding: VDB
    protected val binding: VDB get() = _binding
    protected abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VDB

    override fun initializeBinding() {
        val inflater = WeakReference(LayoutInflater.from(context)).get()
        inflater?.let { _binding = bindingInflater.invoke(it, this, true) }
    }
}
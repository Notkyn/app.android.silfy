package ua.notky.base.ui.dialog.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

abstract class BaseBindingDialogFragment<VDB : ViewDataBinding> : BaseDialogFragment() {

    private var _binding: ViewDataBinding? = null

    @Suppress("UNCHECKED_CAST")
    protected val binding: VDB get() = requireNotNull(_binding) as VDB

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VDB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return requireNotNull(_binding).root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package ua.notky.base.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.ViewDataBinding

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */
abstract class BaseBindingActivity<VDB: ViewDataBinding> : BaseNavigationActivity() {
    private var _binding: ViewDataBinding? = null
    abstract val bindingInflater: (LayoutInflater) -> VDB

    @Suppress("UNCHECKED_CAST")
    protected val binding: VDB get() = requireNotNull(_binding) as VDB


    override fun init(savedInstanceState: Bundle?) {
        _binding = bindingInflater.invoke(layoutInflater, )
        setContentView(_binding?.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
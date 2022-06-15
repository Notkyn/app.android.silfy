package ua.notky.silfy.ui.fragment.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.util.toLog
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.databinding.FragmentCategoryOverviewBinding
import ua.notky.silfy.viewmodel.category.CategoryOverviewViewModel

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 15.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class CategoryOverviewFragment : BaseBindingFragment<FragmentCategoryOverviewBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCategoryOverviewBinding
        get() = FragmentCategoryOverviewBinding::inflate

    private val categoryOverviewViewModel by activityViewModels<CategoryOverviewViewModel>()

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(categoryOverviewViewModel)
            .build()
    }

    override fun initializeViews() {
        toLog("Select category: [title=${categoryOverviewViewModel.model.title.get()}]")
    }
}
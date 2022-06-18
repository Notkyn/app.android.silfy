package ua.notky.silfy.ui.fragment.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import ua.notky.base.extension.openSafePopBackstackScreen
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.databinding.FragmentCategoryOverviewBinding
import ua.notky.silfy.ui.adapter.WordAdapter
import ua.notky.silfy.viewmodel.StateViewModel
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
    private val stateViewModel by activityViewModels<StateViewModel>()

    private val wordAdapter: WordAdapter by lazy {
        return@lazy WordAdapter()
    }

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(categoryOverviewViewModel)
            .build()
    }

    override fun initializeViews() {
        binding.state = stateViewModel.state
        binding.model = categoryOverviewViewModel.model

        initializeRecycler()
    }

    private fun initializeRecycler() {
        binding.recycler.adapter = wordAdapter
        binding.recycler.itemAnimator = DefaultItemAnimator()
    }

    override fun initializeListeners() {
        initSortListeners()

        binding.header.handleBackClick { openSafePopBackstackScreen() }
        binding.info.handleEditClick { }
        binding.info.handleDeleteClick { }
    }

    private fun initSortListeners() {
        binding.sortView.handleSortEnClick {
            stateViewModel.setEnSort()
            categoryOverviewViewModel.onSortWords(binding.sortView.getSortParams())
        }
        binding.sortView.handleSortRuClick {
            stateViewModel.setRuSort()
            categoryOverviewViewModel.onSortWords(binding.sortView.getSortParams())
        }
        binding.sortView.handleSortTypeClick {
            stateViewModel.setTypeSort()
            categoryOverviewViewModel.onSortWords(binding.sortView.getSortParams())
        }
        binding.sortView.handleSortStateClick {
            stateViewModel.setStateSort()
            categoryOverviewViewModel.onSortWords(binding.sortView.getSortParams())
        }
    }

    override fun initializeViewModels() {
        stateViewModel.setDefaultSort()
        categoryOverviewViewModel.onSortWords(binding.sortView.getSortParams())
    }
}
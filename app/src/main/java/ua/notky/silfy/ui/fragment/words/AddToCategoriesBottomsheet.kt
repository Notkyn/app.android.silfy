package ua.notky.silfy.ui.fragment.words

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ua.notky.base.extension.observe
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.databinding.BottomsheetAddToCategoriesBinding
import ua.notky.silfy.databinding.ItemCategoryCheckBinding
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.models.model.WordForm
import ua.notky.silfy.ui.adapter.decorators.ListCardDividerDecoration
import ua.notky.silfy.ui.dialog.BaseSilfyBottomSheet
import ua.notky.silfy.ui.view.avatar.setCategoryTile
import ua.notky.silfy.viewmodel.words.WordsEditViewModel

/**
 * 2d Add to categories: a tap checks / unchecks a category of the word right away, "Done" closes.
 * Shown by WordsEditFragment (child fragment) and shares its view model.
 */
class AddToCategoriesBottomsheet : BaseSilfyBottomSheet<BottomsheetAddToCategoriesBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BottomsheetAddToCategoriesBinding
        get() = BottomsheetAddToCategoriesBinding::inflate

    private val wordsEditViewModel by viewModels<WordsEditViewModel>(ownerProducer = { requireParentFragment() })

    private val categoryAdapter = CategoryCheckAdapter { wordsEditViewModel.toggleCategory(it) }

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(wordsEditViewModel)
            .build()
    }

    override fun initializeViews() {
        binding.recycler.adapter = categoryAdapter
        binding.recycler.itemAnimator = null
        binding.recycler.addItemDecoration(ListCardDividerDecoration(requireContext()))
    }

    override fun initializeListeners() {
        binding.buttonDone.setOnClickListener { dismiss() }
    }

    override fun initializeViewModels() {
        viewLifecycleOwner.observe(wordsEditViewModel.categories) { render() }
        viewLifecycleOwner.observe(wordsEditViewModel.form) { render() }
    }

    private fun render() {
        val categories = wordsEditViewModel.categories.value.orEmpty()
        val form: WordForm? = wordsEditViewModel.form.value
        val selectedIds = form?.categories.orEmpty().mapNotNull { it.id }.toSet()

        categoryAdapter.submitList(
            categories.map { category -> CategoryCheckAdapter.Item(category, category.id in selectedIds) }
        )

        binding.recycler.isVisible = categories.isNotEmpty()
        binding.textEmpty.isVisible = categories.isEmpty()
    }

    companion object {
        const val TAG = "AddToCategoriesBottomsheet"
    }
}

class CategoryCheckAdapter(
    private val onClick: (Category) -> Unit
) : ListAdapter<CategoryCheckAdapter.Item, CategoryCheckAdapter.ViewHolder>(DIFF) {

    data class Item(
        val category: Category,
        val isChecked: Boolean
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryCheckBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemCategoryCheckBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {
            binding.textInitial.setCategoryTile(item.category.id, item.category.title)
            binding.textName.text = item.category.title
            binding.check.isChecked = item.isChecked
            binding.root.setOnClickListener { onClick(item.category) }
        }
    }

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item) = oldItem.category.id == newItem.category.id
            override fun areContentsTheSame(oldItem: Item, newItem: Item) = oldItem == newItem
        }
    }
}

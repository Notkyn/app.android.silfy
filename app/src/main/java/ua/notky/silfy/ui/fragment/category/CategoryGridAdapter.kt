package ua.notky.silfy.ui.fragment.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ua.notky.silfy.R
import ua.notky.silfy.databinding.ItemCategoryBinding
import ua.notky.silfy.databinding.ItemCategoryNewBinding
import ua.notky.silfy.models.model.CategorySummary
import ua.notky.silfy.ui.view.avatar.setCategoryTile

/** 3a Categories grid: category cards, then the dashed "New category" card */
class CategoryGridAdapter(
    private val onCategoryClick: (CategorySummary) -> Unit,
    private val onNewClick: () -> Unit
) : ListAdapter<CategoryGridAdapter.Item, RecyclerView.ViewHolder>(DIFF) {

    sealed class Item {
        data class Category(val category: CategorySummary) : Item()
        object New : Item()
    }

    /** Categories go first, "New category" is always the last card */
    fun submitCategories(categories: List<CategorySummary>) {
        submitList(categories.map { Item.Category(it) } + Item.New)
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Item.Category -> R.layout.item_category
            Item.New -> R.layout.item_category_new
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == R.layout.item_category) {
            CategoryViewHolder(ItemCategoryBinding.inflate(inflater, parent, false))
        } else {
            val binding = ItemCategoryNewBinding.inflate(inflater, parent, false)
            binding.root.setOnClickListener { onNewClick() }
            object : RecyclerView.ViewHolder(binding.root) {}
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (holder is CategoryViewHolder && item is Item.Category) holder.bind(item.category)
    }

    inner class CategoryViewHolder(
        private val binding: ItemCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: CategorySummary) {
            binding.textInitial.setCategoryTile(category.id, category.title)
            binding.textName.text = category.title
            binding.textCount.text = binding.root.resources.getQuantityString(
                R.plurals.plural_words, category.wordCount, category.wordCount
            )
            binding.root.setOnClickListener { onCategoryClick(category) }
        }
    }

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return when {
                    oldItem is Item.Category && newItem is Item.Category -> oldItem.category.id == newItem.category.id
                    else -> oldItem === newItem
                }
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item) = oldItem == newItem
        }
    }
}

package ua.notky.silfy.ui.fragment.words

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ua.notky.silfy.databinding.ItemDictionaryWordBinding
import ua.notky.silfy.models.model.Word
import ua.notky.silfy.ui.view.level.levelName

/**
 * 2a/2b Dictionary rows; also 3b Category rows with [showLevelName] = false (level bars only, as in the design)
 */
class DictionaryWordAdapter(
    private val showLevelName: Boolean = true,
    private val onClick: (Word) -> Unit
) : ListAdapter<Word, DictionaryWordAdapter.ViewHolder>(DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDictionaryWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemDictionaryWordBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(word: Word) {
            binding.textEn.text = word.en
            binding.textTranslation.text = word.translation
            binding.iconFavourite.isVisible = word.isFavourite
            binding.iconBlacklist.isVisible = word.isBlacklist
            binding.level.setWordState(word.state)
            binding.textLevel.isVisible = showLevelName
            if (showLevelName) binding.textLevel.setText(word.state.levelName)
            binding.root.setOnClickListener { onClick(word) }
        }
    }

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<Word>() {
            override fun areItemsTheSame(oldItem: Word, newItem: Word) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Word, newItem: Word) = oldItem == newItem
        }
    }
}

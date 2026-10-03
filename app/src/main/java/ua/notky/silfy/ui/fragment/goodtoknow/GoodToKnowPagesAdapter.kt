package ua.notky.silfy.ui.fragment.goodtoknow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import ua.notky.silfy.R
import ua.notky.silfy.databinding.ItemGtkDifficultyBinding
import ua.notky.silfy.databinding.ItemGtkLevelRowBinding
import ua.notky.silfy.databinding.ItemGtkLevelsBinding
import ua.notky.silfy.databinding.ItemGtkListsBinding
import ua.notky.silfy.databinding.LayoutGtkHeaderBinding
import ua.notky.silfy.models.enums.AppLanguage
import ua.notky.silfy.util.englishLanguageName

/** Pages of "Good to know": levels, difficulty, lists */
class GoodToKnowPagesAdapter(
    private val language: AppLanguage
) : RecyclerView.Adapter<GoodToKnowPagesAdapter.PageHolder>() {

    override fun getItemCount(): Int = PAGES.size

    override fun getItemViewType(position: Int): Int = position

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = when (viewType) {
            PAGE_LEVELS -> ItemGtkLevelsBinding.inflate(inflater, parent, false)
            PAGE_DIFFICULTY -> ItemGtkDifficultyBinding.inflate(inflater, parent, false)
            else -> ItemGtkListsBinding.inflate(inflater, parent, false)
        }
        return PageHolder(binding)
    }

    override fun onBindViewHolder(holder: PageHolder, position: Int) {
        when (val binding = holder.binding) {
            is ItemGtkLevelsBinding -> {
                bindHeader(binding.header, position)
                bindLevels(binding)
            }
            is ItemGtkDifficultyBinding -> {
                bindHeader(binding.header, position)
                val context = binding.root.context
                binding.textLanguagePair.text = context.getString(
                    R.string.gtk_language_pair,
                    context.englishLanguageName(),
                    language.nativeName
                )
            }
            is ItemGtkListsBinding -> bindHeader(binding.header, position)
        }
    }

    private fun bindHeader(header: LayoutGtkHeaderBinding, position: Int) {
        val page = PAGES[position]
        val context = header.root.context
        header.textKicker.text = context.getString(
            R.string.gtk_kicker,
            position + 1,
            PAGES.size,
            context.getString(page.section)
        )
        header.textTitle.setText(page.title)
        header.textLead.setText(page.lead)
    }

    private fun bindLevels(binding: ItemGtkLevelsBinding) {
        if (binding.listLevels.childCount > 0) return

        val inflater = LayoutInflater.from(binding.root.context)
        LEVELS.forEach { (level, name, description) ->
            val row = ItemGtkLevelRowBinding.inflate(inflater, binding.listLevels, false)
            row.level.level = level
            row.textName.setText(name)
            row.textDescription.setText(description)
            binding.listLevels.addView(row.root)
        }
    }

    class PageHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)

    private data class Page(@StringRes val section: Int, @StringRes val title: Int, @StringRes val lead: Int)

    private data class Level(val level: Int, @StringRes val name: Int, @StringRes val description: Int)

    companion object {
        const val PAGE_LEVELS = 0
        const val PAGE_DIFFICULTY = 1
        const val PAGE_LISTS = 2

        private val PAGES = listOf(
            Page(R.string.gtk_section_progress, R.string.gtk_title_levels, R.string.gtk_lead_levels),
            Page(R.string.gtk_section_difficulty, R.string.gtk_title_difficulty, R.string.gtk_lead_difficulty),
            Page(R.string.gtk_section_lists, R.string.gtk_title_lists, R.string.gtk_lead_lists)
        )

        /** From Excellent down to Unknown, as in the design */
        private val LEVELS = listOf(
            Level(4, R.string.level_name_excellent, R.string.level_desc_excellent),
            Level(3, R.string.level_name_good, R.string.level_desc_good),
            Level(2, R.string.level_name_average, R.string.level_desc_average),
            Level(1, R.string.level_name_poor, R.string.level_desc_poor),
            Level(0, R.string.level_name_unknown, R.string.level_desc_unknown)
        )
    }
}

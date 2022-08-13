package ua.notky.silfy.ui.layout.menu

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import ua.notky.base.ui.layout.frame.BaseBindingFrameLayout
import ua.notky.silfy.R
import ua.notky.silfy.databinding.LayoutCardDictionaryBinding
import ua.notky.silfy.extension.showAlert
import ua.notky.silfy.models.enums.DictionaryCardType
import ua.notky.silfy.models.model.DictionaryInfo
import ua.notky.silfy.ui.adapter.DictionaryStatesInfoAdapter

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 29.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class DictionaryCardLayout(context: Context, attrs: AttributeSet? = null) :
    BaseBindingFrameLayout<LayoutCardDictionaryBinding>(context, attrs) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> LayoutCardDictionaryBinding
        get() = LayoutCardDictionaryBinding::inflate

    private lateinit var adapter: DictionaryStatesInfoAdapter
    private var clearAction: (() -> Unit)? = null
    private var defaultAction: (() -> Unit)? = null

    override fun initializeViews() {
        adapter = DictionaryStatesInfoAdapter()
        binding.recycler.adapter = adapter
    }

    fun setType(type: DictionaryCardType) {
        binding.type = type
    }

    fun setContent(value: DictionaryInfo) {
        val count = when (binding.type) {
            DictionaryCardType.ALL -> value.allWords
            DictionaryCardType.FAVOURITE -> value.favouriteWords
            DictionaryCardType.BLACK -> value.blackWords
            else -> 0
        }

        binding.content = count

        if (binding.type == DictionaryCardType.ALL) {
            adapter.clearAndAddAll(value.byStateStats)
        }
    }

    override fun initializeListeners() {
        binding.buttonSettings.setOnClickListener {
            when (binding.type) {
                DictionaryCardType.ALL -> showAllWordsPopup(it)
                DictionaryCardType.FAVOURITE -> showListWordsPopup(it)
                DictionaryCardType.BLACK -> showListWordsPopup(it)
                null -> {}
            }
        }
    }

    private fun showAllWordsPopup(view: View) {
        val popup = PopupMenu(context, view)
        popup.inflate(R.menu.menu_dictionary_all)

        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_clear_progress -> showCleanDictionaryAlert()
                R.id.item_set_default -> showDefaultWordsAlert()
            }
            return@setOnMenuItemClickListener true
        }

        popup.show()
    }

    private fun showListWordsPopup(view: View) {
        val popup = PopupMenu(context, view)
        popup.inflate(R.menu.menu_dictionary_lists)

        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_clear -> showCleanDictionaryAlert()
            }
            return@setOnMenuItemClickListener true
        }

        popup.show()
    }

    fun handleClear(action: () -> Unit) {
        clearAction = action
    }

    fun handleDefaultClick(action: () -> Unit) {
        defaultAction = action
    }

    private fun showCleanDictionaryAlert() {
        binding.type?.let {
            showAlert(
                context = context,
                title = context.getString(it.alertTitle),
                onSuccess = { clearAction?.invoke() }
            )
        } ?: throw IllegalStateException("Unknown Dictionary type")
    }

    private fun showDefaultWordsAlert() {
        binding.type?.let {
            showAlert(
                context = context,
                title = context.getString(R.string.alert_title_set_default_words),
                message = context.getString(R.string.alert_message_set_default_words),
                onSuccess = { defaultAction?.invoke() }
            )
        } ?: throw IllegalStateException("Unknown Dictionary type")
    }
}
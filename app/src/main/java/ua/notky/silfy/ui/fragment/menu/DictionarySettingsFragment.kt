package ua.notky.silfy.ui.fragment.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ua.notky.base.extension.observe
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.R
import ua.notky.silfy.databinding.FragmentDictionarySettingsBinding
import ua.notky.silfy.databinding.ItemDictionaryCounterBinding
import ua.notky.silfy.models.model.WordCounts
import ua.notky.silfy.ui.dialog.DialogTone
import ua.notky.silfy.ui.dialog.showSilfyDialog
import ua.notky.silfy.ui.view.applySystemBarsPadding
import ua.notky.silfy.ui.view.setLightSystemBars
import ua.notky.silfy.viewmodel.menu.DictionarySettingsViewModel
import ua.notky.silfy.viewmodel.menu.DictionarySettingsViewModel.Action
import ua.notky.silfy.viewmodel.menu.DictionarySettingsViewModel.State

/**
 * 6c Dictionary: All words (Reset progress / Default set), Favourites / Blacklist (Clear). Every action asks
 * first (6d) and then shows a success banner. "Clear" is disabled while the list is empty.
 */
@AndroidEntryPoint
class DictionarySettingsFragment : BaseBindingFragment<FragmentDictionarySettingsBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDictionarySettingsBinding
        get() = FragmentDictionarySettingsBinding::inflate

    private val settingsViewModel by viewModels<DictionarySettingsViewModel>()

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(settingsViewModel)
            .build()
    }

    override fun onResume() {
        super.onResume()
        setLightSystemBars(true)
    }

    override fun initializeViews() {
        binding.root.applySystemBarsPadding(top = true)
        binding.scroll.applySystemBarsPadding(bottom = true)

        setupCounter(binding.counterAll, R.drawable.ic_lc_library, R.color.aqua_tint, R.color.ink, R.string.dict_settings_all)
        setupCounter(binding.counterFavourites, R.drawable.ic_lc_star, R.color.fav_bg, R.color.fav_icon, R.string.list_favourites)
        setupCounter(binding.counterBlacklist, R.drawable.ic_lc_ban, R.color.seg_bg_light, R.color.ink, R.string.list_blacklist)
    }

    override fun initializeListeners() {
        binding.buttonBack.setOnClickListener { findNavController().navigateUp() }
        binding.buttonReset.setOnClickListener { confirm(Action.RESET_PROGRESS) }
        binding.buttonDefault.setOnClickListener { confirm(Action.DEFAULT_SET) }
        binding.buttonClearFavourites.setOnClickListener { confirm(Action.CLEAR_FAVOURITES) }
        binding.buttonClearBlacklist.setOnClickListener { confirm(Action.CLEAR_BLACKLIST) }
    }

    override fun initializeViewModels() {
        viewLifecycleOwner.observe(settingsViewModel.counts) { renderButtons() }
        viewLifecycleOwner.observe(settingsViewModel.state, ::renderState)
    }

    private fun setupCounter(
        counter: ItemDictionaryCounterBinding,
        @DrawableRes icon: Int,
        @ColorRes tile: Int,
        @ColorRes iconColor: Int,
        @StringRes label: Int
    ) {
        counter.icon.setImageResource(icon)
        ImageViewCompat.setImageTintList(counter.icon, ContextCompat.getColorStateList(requireContext(), iconColor))
        counter.iconTile.backgroundTintList = ContextCompat.getColorStateList(requireContext(), tile)
        counter.textLabel.setText(label)
    }

    private fun renderButtons() {
        val counts = settingsViewModel.counts.value ?: WordCounts()
        val isIdle = settingsViewModel.state.value != State.Working

        binding.counterAll.textValue.text = counts.total.toString()
        binding.counterFavourites.textValue.text = counts.favourites.toString()
        binding.counterBlacklist.textValue.text = counts.blacklist.toString()

        binding.buttonReset.isEnabled = isIdle
        binding.buttonDefault.isEnabled = isIdle
        binding.buttonClearFavourites.isEnabled = isIdle && counts.favourites > 0
        binding.buttonClearBlacklist.isEnabled = isIdle && counts.blacklist > 0
    }

    private fun renderState(state: State?) {
        renderButtons()

        when (state) {
            is State.Done -> {
                binding.textDone.setText(doneText(state.action))
                binding.textDone.isVisible = true
            }
            State.Working -> binding.textDone.isVisible = false
            State.Failure -> {
                settingsViewModel.consumeFailure()
                showSilfyDialog(
                    icon = R.drawable.ic_lc_triangle_alert,
                    tone = DialogTone.WARNING,
                    title = getString(R.string.dict_settings_error),
                    message = getString(R.string.create_error_message),
                    cancelText = null
                )
            }
            else -> {}
        }
    }

    /** 6d confirmation dialogs */
    private fun confirm(action: Action) {
        val (icon, tone) = when (action) {
            Action.RESET_PROGRESS -> R.drawable.ic_lc_rotate_ccw to DialogTone.NEUTRAL
            Action.DEFAULT_SET -> R.drawable.ic_lc_list_restart to DialogTone.WARNING
            Action.CLEAR_FAVOURITES -> R.drawable.ic_lc_eraser to DialogTone.WARNING
            Action.CLEAR_BLACKLIST -> R.drawable.ic_lc_eraser to DialogTone.NEUTRAL
        }
        val (title, message, ok) = when (action) {
            Action.RESET_PROGRESS -> Triple(
                R.string.dict_settings_reset_title, R.string.dict_settings_reset_message, R.string.dict_settings_reset_button
            )
            Action.DEFAULT_SET -> Triple(
                R.string.dict_settings_default_title, R.string.dict_settings_default_message, R.string.dict_settings_default_button
            )
            Action.CLEAR_FAVOURITES -> Triple(
                R.string.dict_settings_favourites_title, R.string.dict_settings_favourites_message, R.string.dict_settings_clear
            )
            Action.CLEAR_BLACKLIST -> Triple(
                R.string.dict_settings_blacklist_title, R.string.dict_settings_blacklist_message, R.string.dict_settings_clear
            )
        }

        showSilfyDialog(
            icon = icon,
            tone = tone,
            title = getString(title),
            message = getString(message),
            okText = getString(ok),
            onOk = { settingsViewModel.run(action) }
        )
    }

    @StringRes
    private fun doneText(action: Action): Int {
        return when (action) {
            Action.RESET_PROGRESS -> R.string.dict_settings_done_reset
            Action.DEFAULT_SET -> R.string.dict_settings_done_default
            Action.CLEAR_FAVOURITES -> R.string.dict_settings_done_favourites
            Action.CLEAR_BLACKLIST -> R.string.dict_settings_done_blacklist
        }
    }
}

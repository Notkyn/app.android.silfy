package ua.notky.silfy.ui.fragment.profile

import android.content.Intent
import android.content.res.ColorStateList
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ua.notky.base.extension.observe
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.R
import ua.notky.silfy.databinding.FragmentProfileBinding
import ua.notky.silfy.databinding.ItemProgressTileBinding
import ua.notky.silfy.models.model.Profile
import ua.notky.silfy.models.model.ProfileStats
import ua.notky.silfy.models.states.DeleteProfileUiState
import ua.notky.silfy.ui.activity.AuthActivity
import ua.notky.silfy.ui.dialog.DialogTone
import ua.notky.silfy.ui.dialog.profile.EditProfileBottomsheet
import ua.notky.silfy.ui.dialog.profile.SwitchProfileBottomsheet
import ua.notky.silfy.ui.dialog.showSilfyDialog
import ua.notky.silfy.ui.view.level.levelColor
import ua.notky.silfy.ui.view.level.levelName
import ua.notky.silfy.ui.view.setLightSystemBars
import ua.notky.silfy.util.englishLanguageName
import ua.notky.silfy.util.uiLocale
import ua.notky.silfy.viewmodel.profile.ProfileViewModel
import java.text.SimpleDateFormat
import java.util.Date

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

/** 5a Profile: avatar and name, translation language, progress by level, switch / delete profile */
@AndroidEntryPoint
class ProfileFragment : BaseBindingFragment<FragmentProfileBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileBinding
        get() = FragmentProfileBinding::inflate

    private val profileViewModel by viewModels<ProfileViewModel>()

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(profileViewModel)
            .build()
    }

    override fun onResume() {
        super.onResume()
        setLightSystemBars(true)
    }

    override fun initializeViews() {
        applyInsets()

        setupTile(binding.tileExcellent, R.string.level_name_excellent, R.color.success_bg, R.color.success_text, R.color.success_text_2)
        setupTile(binding.tileFavourites, R.string.list_favourites, R.color.fav_bg, R.color.fav_text, R.color.fav_text_2)
        setupTile(binding.tileBlacklist, R.string.list_blacklist, R.color.seg_bg_light, R.color.ink, R.color.text_secondary)
    }

    override fun initializeListeners() {
        binding.buttonEdit.setOnClickListener { EditProfileBottomsheet.show(childFragmentManager) }
        binding.rowSwitch.setOnClickListener { SwitchProfileBottomsheet.show(childFragmentManager) }
        binding.rowDelete.setOnClickListener { showDeleteDialog() }
    }

    override fun initializeViewModels() {
        viewLifecycleOwner.observe(profileViewModel.profile, ::renderProfile)
        viewLifecycleOwner.observe(profileViewModel.stats, ::renderStats)
        viewLifecycleOwner.observe(profileViewModel.deleteState, ::renderDeleteState)
    }

    /** Status bar on top; the last card scrolls above the floating navigation */
    private fun applyInsets() {
        val initialTop = binding.root.paddingTop
        val initialBottom = binding.root.paddingBottom
        val navInset = resources.getDimensionPixelSize(R.dimen.ds_nav_content_inset)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(top = initialTop + bars.top, bottom = initialBottom + navInset + bars.bottom)
            insets
        }
        ViewCompat.requestApplyInsets(binding.root)
    }

    private fun setupTile(
        tile: ItemProgressTileBinding,
        @StringRes label: Int,
        @ColorRes background: Int,
        @ColorRes valueColor: Int,
        @ColorRes labelColor: Int
    ) {
        tile.root.backgroundTintList = ContextCompat.getColorStateList(requireContext(), background)
        tile.textValue.setTextColor(ContextCompat.getColor(requireContext(), valueColor))
        tile.textLabel.setTextColor(ContextCompat.getColor(requireContext(), labelColor))
        tile.textLabel.setText(label)
    }

    private fun renderProfile(profile: Profile?) {
        profile ?: return

        binding.avatar.setProfile(profile)
        binding.textName.text = profile.name
        binding.textSince.text = getString(R.string.profile_since, formatDate(profile.createTime))
        binding.textLanguage.text = getString(
            R.string.session_direction,
            requireContext().englishLanguageName(),
            profile.language.nativeName
        )
        binding.textBadge.text = profile.language.badge
    }

    /** "Sep 12, 2026" / "12 вер. 2026 р." — in the UI language */
    private fun formatDate(time: Long): String {
        val locale = requireContext().uiLocale()
        val pattern = DateFormat.getBestDateTimePattern(locale, DATE_SKELETON)
        return SimpleDateFormat(pattern, locale).format(Date(time))
    }

    private fun renderStats(stats: ProfileStats?) {
        val data = stats ?: ProfileStats()

        binding.textTotal.text = resources.getQuantityString(R.plurals.plural_words, data.total, data.total)
        binding.distribution.setLevels(data.levels)
        renderLegend(data)

        binding.tileExcellent.textValue.text = data.excellent.toString()
        binding.tileFavourites.textValue.text = data.favourites.toString()
        binding.tileBlacklist.textValue.text = data.blacklist.toString()
    }

    /** "● Excellent 8" for every level, zeros too */
    private fun renderLegend(stats: ProfileStats) {
        val inflater = LayoutInflater.from(requireContext())
        binding.legend.removeAllViews()

        stats.levels.forEach { (state, count) ->
            val item = inflater.inflate(R.layout.item_level_legend, binding.legend, false) as AppCompatTextView
            item.text = getString(R.string.profile_legend_item, getString(state.levelName), count)

            val dot = ContextCompat.getDrawable(requireContext(), R.drawable.ds_bg_legend_dot)?.mutate()
            item.setCompoundDrawablesRelativeWithIntrinsicBounds(dot, null, null, null)
            TextViewCompat.setCompoundDrawableTintList(
                item,
                ColorStateList.valueOf(ContextCompat.getColor(requireContext(), state.levelColor))
            )
            binding.legend.addView(item)
        }
    }

    private fun showDeleteDialog() {
        val profile = profileViewModel.profile.value ?: return

        showSilfyDialog(
            icon = R.drawable.ic_lc_trash_2,
            tone = DialogTone.DANGER,
            title = getString(R.string.profile_delete_title, profile.name),
            message = getString(R.string.profile_delete_message),
            okText = getString(R.string.profile_delete_button),
            destructive = true,
            onOk = { profileViewModel.delete() }
        )
    }

    private fun renderDeleteState(state: DeleteProfileUiState?) {
        when (state) {
            DeleteProfileUiState.Deleted -> {
                profileViewModel.consumeState()
                goToNextProfiles()
            }
            DeleteProfileUiState.Failure -> {
                profileViewModel.consumeState()
                showSilfyDialog(
                    icon = R.drawable.ic_lc_triangle_alert,
                    tone = DialogTone.WARNING,
                    title = getString(R.string.profile_error_delete),
                    message = getString(R.string.create_error_message),
                    cancelText = null
                )
            }
            else -> {}
        }
    }

    /** "Who's learning" in a new task; it opens "Create profile" when no profiles are left */
    private fun goToNextProfiles() {
        val intent = Intent(requireContext(), AuthActivity::class.java)
            .putExtra(AuthActivity.EXTRA_START, AuthActivity.START_PROFILES)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    private companion object {
        const val DATE_SKELETON = "yMMMd"
    }
}

package ua.notky.silfy.ui.fragment.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ua.notky.base.extension.observe
import ua.notky.base.extension.openLink
import ua.notky.base.extension.openSafeScreen
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.BuildConfig
import ua.notky.silfy.R
import ua.notky.silfy.databinding.FragmentMenuBinding
import ua.notky.silfy.databinding.ItemMenuSectionBinding
import ua.notky.silfy.models.model.Profile
import ua.notky.silfy.ui.dialog.profile.SwitchProfileBottomsheet
import ua.notky.silfy.ui.view.setLightSystemBars
import ua.notky.silfy.util.englishLanguageName
import ua.notky.silfy.viewmodel.menu.MenuViewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

/** 6a Menu: active profile + Switch (5c), Training mode / Dictionary / Good to know, Contact us / Privacy policy */
@AndroidEntryPoint
class MenuFragment : BaseBindingFragment<FragmentMenuBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMenuBinding
        get() = FragmentMenuBinding::inflate

    private val menuViewModel by viewModels<MenuViewModel>()

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(menuViewModel)
            .build()
    }

    override fun onResume() {
        super.onResume()
        setLightSystemBars(true)
    }

    override fun initializeViews() {
        applyInsets()

        setupSection(binding.rowTraining, R.drawable.ic_lc_target, R.color.aqua_tint, R.string.setup_title_menu, R.string.menu_training_sub)
        setupSection(binding.rowDictionary, R.drawable.ic_lc_library, R.color.fav_bg, R.string.dictionary_title, R.string.menu_dictionary_sub)
        setupSection(binding.rowGoodToKnow, R.drawable.ic_lc_lightbulb, R.color.avatar_3, R.string.gtk_title, R.string.menu_gtk_sub)

        binding.textFooter.text = getString(R.string.menu_footer, BuildConfig.VERSION_NAME)
    }

    override fun initializeListeners() {
        binding.buttonSwitch.setOnClickListener { SwitchProfileBottomsheet.show(childFragmentManager) }
        binding.rowTraining.root.setOnClickListener {
            openSafeScreen(MenuFragmentDirections.actionFragmentMenuToFragmentTrainingSettingsMenu())
        }
        binding.rowDictionary.root.setOnClickListener {
            openSafeScreen(MenuFragmentDirections.actionFragmentMenuToFragmentDictionaryMenu())
        }
        binding.rowGoodToKnow.root.setOnClickListener { goToNextGoodToKnow() }
        binding.rowContact.setOnClickListener { menuViewModel.sendContactUsEmail(requireContext()) }
        binding.rowPrivacy.setOnClickListener { openLink(getString(R.string.url_privacy_policy)) }
    }

    override fun initializeViewModels() {
        viewLifecycleOwner.observe(menuViewModel.profile, ::renderProfile)
    }

    /** Status bar on top; the footer scrolls above the floating navigation */
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

    private fun setupSection(
        row: ItemMenuSectionBinding,
        @DrawableRes icon: Int,
        @ColorRes tile: Int,
        @StringRes title: Int,
        @StringRes subtitle: Int
    ) {
        row.icon.setImageResource(icon)
        row.iconTile.backgroundTintList = ContextCompat.getColorStateList(requireContext(), tile)
        row.textTitle.setText(title)
        row.textSubtitle.setText(subtitle)
    }

    private fun renderProfile(profile: Profile?) {
        profile ?: return

        binding.avatar.setProfile(profile)
        binding.textName.text = profile.name
        binding.textLanguage.text = getString(
            R.string.session_direction,
            requireContext().englishLanguageName(),
            profile.language.nativeName
        )
    }

    /** 6e–6g: the examples are in the profile language */
    private fun goToNextGoodToKnow() {
        val language = menuViewModel.profile.value?.language ?: return
        openSafeScreen(MenuFragmentDirections.actionFragmentMenuToFragmentGoodToKnow(language.code))
    }
}

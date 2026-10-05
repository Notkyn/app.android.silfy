package ua.notky.silfy.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import dagger.hilt.android.AndroidEntryPoint
import ua.notky.base.ui.activity.BaseBindingActivity
import ua.notky.silfy.R
import ua.notky.silfy.databinding.ActivityMainBinding
import ua.notky.silfy.ui.view.drawBehindSystemBars
import ua.notky.silfy.ui.view.nav.SilfyBottomNav
import ua.notky.silfy.viewmodel.StateViewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

/**
 * Words · Categories · Profile · Menu with the floating bottom navigation; Start opens a training session.
 * Silfy 2.0 screens handle system bar insets and the space under the navigation themselves,
 * screens not migrated yet get them as padding of the nav host.
 */
@AndroidEntryPoint
class MainActivity : BaseBindingActivity<ActivityMainBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    private val stateViewModel by viewModels<StateViewModel>()

    private var systemBars: Insets = Insets.NONE
    private var destinationId: Int? = null

    override fun setNavController(): Int {
        return R.id.nav_host_fragment
    }

    override fun initialize(savedInstanceState: Bundle?) {
        super.initialize(savedInstanceState)
        drawBehindSystemBars()
    }

    override fun initializeViews() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            renderChrome()
            insets
        }
    }

    override fun initializeListeners() {
        binding.bottomNav.setOnTabClickListener { navigateToTab(it) }
        binding.bottomNav.setOnStartClickListener { goToNextGoActivity() }
    }

    override fun onInitNavController() {
        mNavController.addOnDestinationChangedListener { _, destination, _ ->
            destinationId = destination.id
            renderChrome()
        }
    }

    private fun navigateToTab(tab: SilfyBottomNav.Tab) {
        if (tabOf(destinationId) != tab) stateViewModel.setDefaultSort()

        when (tab) {
            SilfyBottomNav.Tab.WORDS -> mNavController.navigate(R.id.action_global_to_fragmentWords)
            SilfyBottomNav.Tab.CATEGORIES -> mNavController.navigate(R.id.action_global_to_fragmentCategory)
            SilfyBottomNav.Tab.PROFILE -> mNavController.navigate(R.id.action_global_to_fragmentProfile)
            SilfyBottomNav.Tab.MENU -> mNavController.navigate(R.id.action_global_to_fragmentMenu)
        }
    }

    /** Navigation visibility and selection, insets for the screens not migrated to Silfy 2.0 yet */
    private fun renderChrome() {
        val id = destinationId
        val isNavVisible = id !in DESTINATIONS_WITHOUT_NAV

        binding.bottomNav.isVisible = isNavVisible
        tabOf(id)?.let { binding.bottomNav.selectedTab = it }
        binding.bottomNav.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            bottomMargin = resources.getDimensionPixelSize(R.dimen.ds_nav_margin_bottom) + systemBars.bottom
        }

        val isV2 = id in V2_DESTINATIONS
        val navInset = if (isNavVisible) resources.getDimensionPixelSize(R.dimen.ds_nav_content_inset) else 0
        binding.navHostFragment.updatePadding(
            top = if (isV2) 0 else systemBars.top,
            bottom = if (isV2) 0 else navInset + systemBars.bottom
        )
    }

    private fun goToNextGoActivity() {
        mNavController.navigate(R.id.to_activity_go)
    }

    private companion object {
        /** Draw behind the system bars and the navigation themselves */
        val V2_DESTINATIONS = setOf(R.id.fragment_words, R.id.fragment_words_edit)

        val DESTINATIONS_WITHOUT_NAV = setOf(R.id.fragment_words_edit)

        fun tabOf(destinationId: Int?): SilfyBottomNav.Tab? {
            return when (destinationId) {
                R.id.fragment_words -> SilfyBottomNav.Tab.WORDS
                R.id.fragment_category,
                R.id.fragment_category_overview -> SilfyBottomNav.Tab.CATEGORIES
                R.id.fragment_profile -> SilfyBottomNav.Tab.PROFILE
                R.id.fragment_menu,
                R.id.fragment_menu_profile,
                R.id.fragment_menu_dictionary,
                R.id.fragment_menu_training_settings,
                R.id.fragment_menu_info,
                R.id.fragment_menu_admin -> SilfyBottomNav.Tab.MENU
                else -> null
            }
        }
    }
}

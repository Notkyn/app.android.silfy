package ua.notky.silfy.ui.fragment.go

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import ua.notky.base.extension.observe
import ua.notky.base.extension.openSafePopBackstackScreen
import ua.notky.base.extension.openSafeScreen
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.R
import ua.notky.silfy.databinding.FragmentSessionSetupBinding
import ua.notky.silfy.models.enums.AppLanguage
import ua.notky.silfy.models.enums.DifficultType
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.models.model.SessionSettings
import ua.notky.silfy.ui.activity.GoActivity
import ua.notky.silfy.ui.dialog.DialogTone
import ua.notky.silfy.ui.dialog.showSilfyDialog
import ua.notky.silfy.ui.view.applySystemBarsPadding
import ua.notky.silfy.ui.view.setLightSystemBars
import ua.notky.silfy.viewmodel.go.SessionSetupViewModel

/**
 * 4a New session (GoActivity, ✕ + Start) and 6b Training mode (menu, ← + Save settings):
 * the same cards, the mode comes from the `isMenu` argument.
 */
@AndroidEntryPoint
class SessionSetupFragment : BaseBindingFragment<FragmentSessionSetupBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSessionSetupBinding
        get() = FragmentSessionSetupBinding::inflate

    private val setupViewModel by viewModels<SessionSetupViewModel>()

    private val isMenu: Boolean get() = arguments?.getBoolean(ARG_IS_MENU) ?: false

    /** Chips are built once per category list; the selection only checks them */
    private var renderedCategories: List<Category>? = null

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(setupViewModel)
            .build()
    }

    override fun onResume() {
        super.onResume()
        setLightSystemBars(true)
    }

    override fun initializeViews() {
        binding.root.applySystemBarsPadding(top = true)
        binding.bottomBar.applySystemBarsPadding(bottom = true)
        renderedCategories = null

        binding.buttonBack.setIconResource(if (isMenu) R.drawable.ic_lc_arrow_left else R.drawable.ic_lc_x)
        binding.textTitle.setText(if (isMenu) R.string.setup_title_menu else R.string.setup_title_start)
        initMainButton()

        binding.segmentedDifficulty.setOptions(
            listOf(getString(R.string.difficulty_easy), getString(R.string.difficulty_hard))
        )
        binding.segmentedDuration.setOptions(SessionSettings.DURATIONS.map { getString(R.string.setup_minutes, it) })
        binding.segmentedMistakes.setOptions(SessionSettings.MISTAKE_LIMITS.map { it.toString() })
        binding.segmentedWords.setOptions(
            listOf(getString(R.string.setup_words_all), getString(R.string.list_favourites))
        )
    }

    /** Start: aqua with ink "play"; Save settings: ink with aqua "check" */
    private fun initMainButton() {
        val button = binding.buttonMain
        if (isMenu) {
            button.setText(R.string.setup_save)
            button.setIconResource(R.drawable.ic_lc_check)
            button.setTextColor(requireContext().getColor(R.color.text_on_ink))
            button.backgroundTintList = requireContext().getColorStateList(R.color.ds_btn_primary_bg)
            button.iconTint = requireContext().getColorStateList(R.color.aqua)
        } else {
            button.setText(R.string.setup_start)
            button.setIconResource(R.drawable.ic_lc_play)
        }
    }

    override fun initializeListeners() {
        binding.buttonBack.setOnClickListener { close() }

        binding.segmentedDifficulty.setOnOptionSelectedListener {
            setupViewModel.setDifficulty(if (it == 0) DifficultType.EASY else DifficultType.HARD)
        }
        binding.segmentedDuration.setOnOptionSelectedListener {
            setupViewModel.setMinutes(SessionSettings.DURATIONS[it])
        }
        binding.rowMistakeLimit.setOnClickListener {
            setupViewModel.setMistakeLimit(!binding.switchMistakeLimit.isChecked)
        }
        binding.segmentedMistakes.setOnOptionSelectedListener {
            setupViewModel.setMaxMistakes(SessionSettings.MISTAKE_LIMITS[it])
        }
        binding.segmentedWords.setOnOptionSelectedListener { setupViewModel.setFavouritesOnly(it == 1) }
        binding.rowBlacklist.setOnClickListener {
            setupViewModel.setBlacklistIncluded(!binding.switchBlacklist.isChecked)
        }

        binding.buttonMain.setOnClickListener { setupViewModel.submit(isMenu) }
    }

    override fun initializeViewModels() {
        viewLifecycleOwner.observe(setupViewModel.categories, ::renderCategories)
        viewLifecycleOwner.observe(setupViewModel.settings, ::renderSettings)
        viewLifecycleOwner.observe(setupViewModel.language, ::renderLanguage)
        viewLifecycleOwner.observe(setupViewModel.matchCount, ::renderMatchCount)
        viewLifecycleOwner.observe(setupViewModel.event, ::renderEvent)
    }

    private fun renderSettings(settings: SessionSettings?) {
        settings ?: return

        binding.segmentedDifficulty.selectedIndex = if (settings.isHard) 1 else 0
        renderDifficultyNote()
        binding.segmentedDuration.selectedIndex = SessionSettings.DURATIONS.indexOf(settings.minutes).coerceAtLeast(0)
        binding.switchMistakeLimit.isChecked = settings.isMistakeLimit
        binding.segmentedMistakes.isVisible = settings.isMistakeLimit
        binding.segmentedMistakes.selectedIndex =
            SessionSettings.MISTAKE_LIMITS.indexOf(settings.maxMistakes).coerceAtLeast(0)
        binding.segmentedWords.selectedIndex = if (settings.isFavouritesOnly) 1 else 0
        binding.switchBlacklist.isChecked = settings.isBlacklistIncluded

        renderCategoryChecks(settings.categoryIds)
    }

    private fun renderLanguage(language: AppLanguage?) {
        renderDifficultyNote()
    }

    private fun renderDifficultyNote() {
        val isHard = setupViewModel.settings.value?.isHard == true
        val language = setupViewModel.language.value
        binding.textDifficultyNote.text = when {
            isHard -> getString(R.string.setup_hard_note)
            else -> getString(R.string.setup_easy_note, language?.nativeName.orEmpty())
        }
    }

    private fun renderCategories(categories: List<Category>?) {
        val list = categories.orEmpty()
        binding.cardCategories.isVisible = list.isNotEmpty()
        if (list == renderedCategories) return
        renderedCategories = list

        val group = binding.chipsCategories
        group.removeAllViews()
        val inflater = LayoutInflater.from(requireContext())
        list.forEach { category ->
            val id = category.id ?: return@forEach
            val chip = inflater.inflate(R.layout.item_category_filter_chip, group, false) as Chip
            chip.id = ViewGroup.generateViewId()
            chip.tag = id
            chip.text = category.title
            chip.setOnClickListener { setupViewModel.toggleCategory(id) }
            group.addView(chip)
        }

        setupViewModel.settings.value?.let { renderCategoryChecks(it.categoryIds) }
    }

    private fun renderCategoryChecks(selected: Set<Int>) {
        val group = binding.chipsCategories
        for (index in 0 until group.childCount) {
            val chip = group.getChildAt(index) as? Chip ?: continue
            chip.isChecked = chip.tag in selected
        }
    }

    private fun renderMatchCount(count: Int?) {
        val value = count ?: 0
        binding.textMatch.text = resources.getQuantityString(R.plurals.plural_words_match, value, value)
    }

    private fun renderEvent(event: SessionSetupViewModel.Event?) {
        binding.buttonMain.isEnabled = event != SessionSetupViewModel.Event.Saving

        when (event) {
            SessionSetupViewModel.Event.Started -> {
                setupViewModel.consumeEvent()
                openSafeScreen(R.id.action_sessionSetup_to_session)
            }
            SessionSetupViewModel.Event.Saved -> {
                setupViewModel.consumeEvent()
                openSafePopBackstackScreen()
            }
            SessionSetupViewModel.Event.Empty -> {
                setupViewModel.consumeEvent()
                showSilfyDialog(
                    icon = R.drawable.ic_lc_triangle_alert,
                    tone = DialogTone.WARNING,
                    title = getString(R.string.setup_empty_title),
                    message = getString(R.string.setup_empty_message),
                    cancelText = null
                )
            }
            SessionSetupViewModel.Event.Failure -> {
                setupViewModel.consumeEvent()
                showSilfyDialog(
                    icon = R.drawable.ic_lc_triangle_alert,
                    tone = DialogTone.WARNING,
                    title = getString(R.string.setup_error_save),
                    message = getString(R.string.create_error_message),
                    cancelText = null
                )
            }
            else -> {}
        }
    }

    /** ✕ closes the training, ← goes back to the menu */
    private fun close() {
        if (isMenu) {
            openSafePopBackstackScreen()
        } else {
            (activity as? GoActivity)?.close()
        }
    }

    companion object {
        /** Navigation argument (nav_graph_main, 6b): true — "Save settings" */
        const val ARG_IS_MENU = "isMenu"
    }
}

package ua.notky.silfy.ui.fragment.words

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import ua.notky.base.extension.hideKeyboard
import ua.notky.base.extension.observe
import ua.notky.base.extension.openSafePopBackstackScreen
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.R
import ua.notky.silfy.databinding.FragmentWordsEditBinding
import ua.notky.silfy.models.enums.AppLanguage
import ua.notky.silfy.models.model.Category
import ua.notky.silfy.models.model.WordForm
import ua.notky.silfy.models.states.EditWordUiState
import ua.notky.silfy.ui.dialog.DialogTone
import ua.notky.silfy.ui.dialog.showSilfyDialog
import ua.notky.silfy.ui.view.applySystemBarsPadding
import ua.notky.silfy.ui.view.level.MAX_WORD_LEVEL
import ua.notky.silfy.ui.view.level.level
import ua.notky.silfy.ui.view.level.levelColor
import ua.notky.silfy.ui.view.level.levelName
import ua.notky.silfy.ui.view.setLightSystemBars
import ua.notky.silfy.viewmodel.words.WordsEditViewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 23.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

/** 2c Edit / New word, 2d "Add to categories" sheet, 2e "Delete this word?" dialog */
@AndroidEntryPoint
class WordsEditFragment : BaseBindingFragment<FragmentWordsEditBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentWordsEditBinding
        get() = FragmentWordsEditBinding::inflate

    private val wordsEditViewModel by viewModels<WordsEditViewModel>()

    /** Knowledge level scale: bar + label for Unknown … Excellent */
    private val levelSegments = mutableListOf<Pair<View, AppCompatTextView>>()

    /** Chips are rebuilt only when the selection changes, not on every keystroke */
    private var renderedCategories: List<Category>? = null

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(wordsEditViewModel)
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
        initLevelScale()
    }

    override fun initializeListeners() {
        binding.buttonBack.setOnClickListener { onBack() }
        binding.buttonDelete.setOnClickListener { showDeleteWordDialog() }

        binding.editEn.doAfterTextChanged { wordsEditViewModel.setEn(it?.toString().orEmpty()) }
        binding.editTranslation.doAfterTextChanged {
            wordsEditViewModel.setTranslation(it?.toString().orEmpty())
        }
        binding.editTranslation.setOnEditorActionListener { view, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                view.clearFocus()
                view.hideKeyboard()
            }
            false
        }

        binding.buttonAddCategory.setOnClickListener { showAddToCategories() }
        binding.rowFavourite.setOnClickListener { wordsEditViewModel.toggleFavourite() }
        binding.rowBlacklist.setOnClickListener { wordsEditViewModel.toggleBlacklist() }
        binding.buttonResetLevel.setOnClickListener { wordsEditViewModel.resetLevel() }

        binding.buttonSave.setOnClickListener {
            binding.root.hideKeyboard()
            wordsEditViewModel.save()
        }
    }

    override fun initializeViewModels() {
        viewLifecycleOwner.observe(wordsEditViewModel.form, ::renderForm)
        viewLifecycleOwner.observe(wordsEditViewModel.language, ::renderLanguage)
        viewLifecycleOwner.observe(wordsEditViewModel.uiState, ::renderUiState)
    }

    private fun initLevelScale() {
        val inflater = LayoutInflater.from(requireContext())
        levelSegments.clear()

        (0..MAX_WORD_LEVEL).forEach { level ->
            val segment = inflater.inflate(R.layout.item_level_segment, binding.levelScale, false)
            if (level > 0) {
                (segment.layoutParams as ViewGroup.MarginLayoutParams).marginStart =
                    resources.getDimensionPixelSize(R.dimen.ds_segmented_padding)
            }
            val label = segment.findViewById<AppCompatTextView>(R.id.label)
            label.setText(levelName(level))
            levelSegments += segment.findViewById<View>(R.id.bar) to label
            binding.levelScale.addView(segment)
        }
    }

    private fun renderForm(form: WordForm?) {
        form ?: return

        binding.textTitle.setText(if (form.isNew) R.string.word_title_new else R.string.word_title_edit)
        binding.buttonDelete.isVisible = !form.isNew

        binding.editEn.setTextIfChanged(form.en)
        binding.editTranslation.setTextIfChanged(form.translation)
        binding.inputEn.error = when {
            form.isDuplicate -> getString(R.string.word_en_exists)
            form.isEnError -> getString(R.string.word_en_error)
            else -> null
        }
        binding.inputTranslation.error =
            if (form.isTranslationError) getString(R.string.word_translation_error) else null

        renderCategories(form.categories)

        binding.switchFavourite.isChecked = form.isFavourite
        binding.switchBlacklist.isChecked = form.isBlacklist

        binding.cardLevel.isVisible = !form.isNew
        renderLevel(form.state.level)

        renderSaveButton()
    }

    /** Loaded values go into the fields; typing does not move the cursor */
    private fun EditText.setTextIfChanged(value: String) {
        if (text?.toString() != value) setText(value)
    }

    private fun renderCategories(categories: List<Category>) {
        if (categories == renderedCategories) return
        renderedCategories = categories

        val group = binding.chipsCategories
        // Everything but the last child — the dashed "Add"
        group.removeViews(0, group.childCount - 1)

        val inflater = LayoutInflater.from(requireContext())
        categories.forEach { category ->
            val chip = inflater.inflate(R.layout.item_category_chip, group, false) as Chip
            chip.text = category.title
            chip.setOnCloseIconClickListener { wordsEditViewModel.removeCategory(category) }
            group.addView(chip, group.childCount - 1)
        }
    }

    /** Bars up to the current level take its color; the current level name is ink */
    private fun renderLevel(level: Int) {
        val filled = ContextCompat.getColor(requireContext(), levelColor(level))
        val empty = ContextCompat.getColor(requireContext(), R.color.level_empty)
        val labelCurrent = ContextCompat.getColor(requireContext(), R.color.text_primary)
        val labelDefault = ContextCompat.getColor(requireContext(), R.color.text_placeholder)

        levelSegments.forEachIndexed { index, (bar, label) ->
            bar.backgroundTintList = ColorStateList.valueOf(if (index <= level) filled else empty)
            label.setTextColor(if (index == level) labelCurrent else labelDefault)
        }
    }

    private fun renderLanguage(language: AppLanguage?) {
        binding.textLanguageBadge.isVisible = language != null
        binding.textLanguageBadge.text = language?.badge
    }

    private fun renderSaveButton() {
        val form = wordsEditViewModel.form.value
        val isBusy = wordsEditViewModel.uiState.value != EditWordUiState.Idle
        binding.buttonSave.isEnabled = form?.canSave == true && !isBusy
    }

    private fun renderUiState(state: EditWordUiState?) {
        renderSaveButton()

        when (state) {
            EditWordUiState.Saved, EditWordUiState.Deleted -> openSafePopBackstackScreen()
            EditWordUiState.Failure.Load -> showErrorDialog(R.string.word_error_load) { onBack() }
            EditWordUiState.Failure.Save -> showErrorDialog(R.string.word_error_save)
            EditWordUiState.Failure.Delete -> showErrorDialog(R.string.word_error_delete)
            else -> {}
        }
    }

    private fun showErrorDialog(title: Int, onClose: (() -> Unit)? = null) {
        wordsEditViewModel.consumeState()
        showSilfyDialog(
            icon = R.drawable.ic_lc_triangle_alert,
            tone = DialogTone.WARNING,
            title = getString(title),
            message = getString(R.string.create_error_message),
            cancelText = null,
            onDismiss = onClose
        )
    }

    private fun showAddToCategories() {
        binding.root.hideKeyboard()
        if (childFragmentManager.findFragmentByTag(AddToCategoriesBottomsheet.TAG) != null) return
        AddToCategoriesBottomsheet().show(childFragmentManager, AddToCategoriesBottomsheet.TAG)
    }

    private fun showDeleteWordDialog() {
        binding.root.hideKeyboard()
        showSilfyDialog(
            icon = R.drawable.ic_lc_trash_2,
            tone = DialogTone.DANGER,
            title = getString(R.string.word_delete_title),
            message = getString(R.string.word_delete_message),
            okText = getString(R.string.word_delete_button),
            destructive = true,
            onOk = { wordsEditViewModel.delete() }
        )
    }

    private fun onBack() {
        binding.root.hideKeyboard()
        openSafePopBackstackScreen()
    }
}

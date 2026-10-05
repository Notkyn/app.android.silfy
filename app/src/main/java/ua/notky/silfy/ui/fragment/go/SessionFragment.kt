package ua.notky.silfy.ui.fragment.go

import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.addCallback
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.bold
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import ua.notky.base.extension.findSafeNavController
import ua.notky.base.extension.hideKeyboard
import ua.notky.base.extension.observe
import ua.notky.base.extension.openSafeScreen
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.R
import ua.notky.silfy.databinding.FragmentSessionBinding
import ua.notky.silfy.session.SessionQuestion
import ua.notky.silfy.ui.activity.GoActivity
import ua.notky.silfy.ui.dialog.DialogTone
import ua.notky.silfy.ui.dialog.showSilfyDialog
import ua.notky.silfy.ui.view.applySystemBarsPadding
import ua.notky.silfy.ui.view.setLightSystemBars
import ua.notky.silfy.viewmodel.go.SessionViewModel
import ua.notky.silfy.viewmodel.go.SessionViewModel.PauseReason

/**
 * 4c–4h Session and its 4i "End this session?" dialog (✕ or Back). The timer stands while
 * the dialog is open and while the app is in the background.
 */
@AndroidEntryPoint
class SessionFragment : BaseBindingFragment<FragmentSessionBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSessionBinding
        get() = FragmentSessionBinding::inflate

    private val sessionViewModel by activityViewModels<SessionViewModel>()

    private val options: List<MaterialButton>
        get() = listOf(binding.option1, binding.option2, binding.option3, binding.option4)

    /** Letter tiles / options / the field are rebuilt only for a new question */
    private var renderedQuestion: SessionQuestion? = null

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(sessionViewModel)
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when {
            // Opened from the settings or "Another session"
            savedInstanceState == null -> sessionViewModel.start()
            // Restored after the process was killed: the session is gone
            sessionViewModel.step.value == null -> (activity as? GoActivity)?.close()
        }
    }

    override fun onResume() {
        super.onResume()
        setLightSystemBars(true)
        sessionViewModel.resume(PauseReason.BACKGROUND)
    }

    override fun onPause() {
        super.onPause()
        sessionViewModel.pause(PauseReason.BACKGROUND)
    }

    override fun initializeViews() {
        binding.root.applySystemBarsPadding(top = true)
        binding.bottomBar.applySystemBarsPadding(bottom = true)
        renderedQuestion = null
    }

    override fun initializeListeners() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) { showEndDialog() }
        binding.buttonClose.setOnClickListener { showEndDialog() }

        options.forEach { button ->
            button.setOnClickListener { sessionViewModel.choose(button.text.toString()) }
        }

        binding.editAnswer.doAfterTextChanged { sessionViewModel.setTyped(it?.toString().orEmpty()) }
        binding.editAnswer.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) sessionViewModel.check()
            actionId == EditorInfo.IME_ACTION_DONE
        }

        binding.buttonMain.setOnClickListener {
            if (sessionViewModel.state.value?.feedback != null) sessionViewModel.next() else sessionViewModel.check()
        }
    }

    override fun initializeViewModels() {
        viewLifecycleOwner.observe(sessionViewModel.step, ::renderStep)
        viewLifecycleOwner.observe(sessionViewModel.state, ::renderState)
        viewLifecycleOwner.observe(sessionViewModel.secondsLeft, ::renderTimer)
    }

    private fun renderStep(step: SessionViewModel.Step?) {
        when (step) {
            is SessionViewModel.Step.Finished -> {
                binding.root.hideKeyboard()
                if (findSafeNavController()?.currentDestination?.id == R.id.fragment_session) {
                    openSafeScreen(R.id.action_session_to_results)
                }
            }
            SessionViewModel.Step.Empty -> showCloseDialog(R.string.setup_empty_title, R.string.setup_empty_message)
            SessionViewModel.Step.Failure -> showCloseDialog(R.string.session_error_start, R.string.create_error_message)
            else -> {}
        }
    }

    private fun renderTimer(seconds: Int?) {
        val value = seconds ?: 0
        binding.textTimer.text = getString(R.string.session_timer, value / 60, value % 60)
    }

    private fun renderState(state: SessionViewModel.State?) {
        state ?: return
        val question = state.question
        val isNewQuestion = question != renderedQuestion
        renderedQuestion = question

        binding.textNumber.text = getString(R.string.session_word_number, state.number)
        binding.textDirection.text = if (question.isFromEnglish) {
            getString(R.string.session_direction, ENGLISH_BADGE, state.languageBadge)
        } else {
            getString(R.string.session_direction, state.languageBadge, ENGLISH_BADGE)
        }
        binding.textPrompt.text = question.prompt
        binding.textCaption.setText(
            when (question) {
                is SessionQuestion.Choose -> R.string.session_caption_choose
                is SessionQuestion.Letters -> R.string.session_caption_letters
                is SessionQuestion.Type -> R.string.session_caption_type
            }
        )

        binding.textMistakes.isVisible = state.maxMistakes != null
        binding.textMistakes.text = state.maxMistakes?.let { "${state.mistakes}/$it" }

        binding.viewChoose.isVisible = question is SessionQuestion.Choose
        binding.viewLetters.isVisible = question is SessionQuestion.Letters
        binding.viewType.isVisible = question is SessionQuestion.Type

        when (question) {
            is SessionQuestion.Choose -> renderChoose(question, state)
            is SessionQuestion.Letters -> renderLetters(question, state, isNewQuestion)
            is SessionQuestion.Type -> renderType(question, state, isNewQuestion)
        }

        renderFeedback(state)
        renderMainButton(state)
        if (isNewQuestion) binding.scroll.scrollTo(0, 0)
    }

    // ---------- Choose ----------

    private fun renderChoose(question: SessionQuestion.Choose, state: SessionViewModel.State) {
        binding.rowOptions2.isVisible = question.options.size > 2
        options.forEachIndexed { index, button ->
            val option = question.options.getOrNull(index)
            button.isVisible = option != null
            button.text = option
            button.isClickable = state.feedback == null

            val answered = state.feedback != null
            when {
                !answered -> button.setOptionColors(R.color.surface, R.color.card_border, R.color.ink)
                option == question.answer ->
                    button.setOptionColors(R.color.success_bg, R.color.success_border, R.color.success_text)
                option == state.picked ->
                    button.setOptionColors(R.color.error_bg, R.color.error_border, R.color.error_text)
                else -> button.setOptionColors(R.color.surface, R.color.card_border, R.color.text_placeholder)
            }
        }
    }

    private fun MaterialButton.setOptionColors(@ColorRes background: Int, @ColorRes stroke: Int, @ColorRes text: Int) {
        backgroundTintList = ColorStateList.valueOf(color(background))
        strokeColor = ColorStateList.valueOf(color(stroke))
        setTextColor(color(text))
    }

    // ---------- Letters ----------

    private fun renderLetters(question: SessionQuestion.Letters, state: SessionViewModel.State, isNew: Boolean) {
        if (isNew) buildLetterTiles(question)

        val built = state.builtWord
        val feedback = state.feedback
        binding.flexSlots.removeAllViews()
        question.answer.indices.forEach { index ->
            val char = built.getOrNull(index)
            val (fill, stroke) = when {
                feedback != null && feedback.isCorrect -> R.color.success_bg to R.color.success_border
                feedback != null -> R.color.error_bg to R.color.error_border
                char != null -> R.color.surface to R.color.ink
                else -> android.R.color.transparent to R.color.card_border
            }
            binding.flexSlots.addView(createSlot(char?.toString().orEmpty(), fill, stroke))
        }

        // The last child is the erase button
        for (index in 0 until binding.flexTiles.childCount - 1) {
            val tile = binding.flexTiles.getChildAt(index)
            val isUsed = index in state.usedTiles
            tile.alpha = if (isUsed) USED_TILE_ALPHA else 1f
            tile.isEnabled = !isUsed && feedback == null
        }
        binding.flexTiles.getChildAt(binding.flexTiles.childCount - 1)?.isEnabled =
            feedback == null && state.usedTiles.isNotEmpty()
    }

    private fun buildLetterTiles(question: SessionQuestion.Letters) {
        val group = binding.flexTiles
        group.removeAllViews()
        val gap = dp(4)

        question.tiles.forEachIndexed { index, letter ->
            val tile = AppCompatTextView(requireContext()).apply {
                text = letter
                gravity = Gravity.CENTER
                includeFontPadding = false
                textSize = LETTER_TEXT_SP
                typeface = ResourcesCompat.getFont(context, R.font.onest_bold)
                setTextColor(color(R.color.ink))
                setBackgroundResource(R.drawable.ds_bg_letter_tile)
                setOnClickListener { sessionViewModel.tapTile(index) }
            }
            group.addView(tile, FlexboxLayout.LayoutParams(dp(52), dp(56)).apply { setMargins(gap, gap, gap, gap) })
        }

        val erase = AppCompatImageView(requireContext()).apply {
            setImageResource(R.drawable.ic_lc_delete)
            scaleType = android.widget.ImageView.ScaleType.CENTER
            background = rounded(R.color.seg_bg, null, dp(15))
            contentDescription = getString(R.string.session_erase)
            setOnClickListener { sessionViewModel.eraseTile() }
        }
        group.addView(erase, FlexboxLayout.LayoutParams(dp(52), dp(56)).apply { setMargins(gap, gap, gap, gap) })
    }

    private fun createSlot(letter: String, @ColorRes fill: Int, @ColorRes stroke: Int): AppCompatTextView {
        return AppCompatTextView(requireContext()).apply {
            text = letter
            gravity = Gravity.CENTER
            includeFontPadding = false
            textSize = LETTER_TEXT_SP
            typeface = ResourcesCompat.getFont(context, R.font.onest_bold)
            setTextColor(color(R.color.ink))
            background = rounded(fill, stroke, dp(12))
            importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_NO
            layoutParams = FlexboxLayout.LayoutParams(dp(40), dp(50)).apply {
                setMargins(dp(3), dp(3), dp(3), dp(3))
            }
        }
    }

    // ---------- Type ----------

    private fun renderType(question: SessionQuestion.Type, state: SessionViewModel.State, isNew: Boolean) {
        val edit = binding.editAnswer
        if (isNew) {
            edit.setText("")
            edit.isEnabled = true
            edit.requestFocus()
            edit.post { showKeyboard() }
        }

        val feedback = state.feedback
        edit.background = when {
            feedback == null -> rounded(R.color.surface, R.color.input_border, dp(20), dp(2))
            feedback.isCorrect -> rounded(R.color.success_bg, R.color.success_border, dp(20), dp(2))
            else -> rounded(R.color.error_bg, R.color.error_border, dp(20), dp(2))
        }
        if (feedback != null && edit.isEnabled) {
            edit.isEnabled = false
            edit.hideKeyboard()
        }

        binding.textCorrectAnswer.isVisible = feedback != null && !feedback.isCorrect
        binding.textCorrectAnswer.text = SpannableStringBuilder()
            .append(getString(R.string.session_correct_answer))
            .append(" ")
            .bold { append(question.answer) }
    }

    private fun showKeyboard() {
        val edit = bindingOrNull()?.editAnswer ?: return
        val imm = edit.context.getSystemService(InputMethodManager::class.java)
        imm?.showSoftInput(edit, InputMethodManager.SHOW_IMPLICIT)
    }

    /** The view may be gone when a posted call runs */
    private fun bindingOrNull(): FragmentSessionBinding? = if (view != null) binding else null

    // ---------- Feedback, main button ----------

    private fun renderFeedback(state: SessionViewModel.State) {
        val feedback = state.feedback
        binding.viewFeedback.isVisible = feedback != null
        feedback ?: return

        val word = state.question.word
        if (feedback.isCorrect) {
            binding.viewFeedback.backgroundTintList = ColorStateList.valueOf(color(R.color.success_bg))
            binding.iconFeedback.setImageResource(R.drawable.ic_lc_circle_check)
            binding.iconFeedback.imageTintList = ColorStateList.valueOf(color(R.color.success_text))
            binding.textFeedback.setTextColor(color(R.color.success_text))
            binding.textFeedback.text = if (feedback.isLevelUp) {
                getString(R.string.session_feedback_level_up, word.en)
            } else {
                getString(R.string.session_feedback_correct)
            }
        } else {
            binding.viewFeedback.backgroundTintList = ColorStateList.valueOf(color(R.color.error_bg))
            binding.iconFeedback.setImageResource(R.drawable.ic_lc_circle_x)
            binding.iconFeedback.imageTintList = ColorStateList.valueOf(color(R.color.error_text))
            binding.textFeedback.setTextColor(color(R.color.error_text))
            binding.textFeedback.text = getString(R.string.session_feedback_wrong, state.question.answer)
        }
    }

    /** Choose: "Next", disabled until an option is tapped; Letters / Type: "Check", then "Next" */
    private fun renderMainButton(state: SessionViewModel.State) {
        val answered = state.feedback != null
        val isChoose = state.question is SessionQuestion.Choose
        binding.buttonMain.setText(if (answered || isChoose) R.string.session_next else R.string.session_check)
        binding.buttonMain.isEnabled = answered || state.canCheck
    }

    // ---------- Dialogs ----------

    private fun showEndDialog() {
        if (sessionViewModel.step.value != SessionViewModel.Step.Running) return
        binding.root.hideKeyboard()
        sessionViewModel.pause(PauseReason.DIALOG)

        showSilfyDialog(
            icon = R.drawable.ic_lc_log_out,
            tone = DialogTone.NEUTRAL,
            title = getString(R.string.session_end_title),
            message = getString(R.string.session_end_message),
            okText = getString(R.string.session_end_button),
            cancelText = getString(R.string.session_end_keep),
            onOk = { sessionViewModel.end() },
            onDismiss = { sessionViewModel.resume(PauseReason.DIALOG) }
        )
    }

    /** The session cannot go on: say why and close the training */
    private fun showCloseDialog(title: Int, message: Int) {
        showSilfyDialog(
            icon = R.drawable.ic_lc_triangle_alert,
            tone = DialogTone.WARNING,
            title = getString(title),
            message = getString(message),
            cancelText = null,
            onDismiss = { (activity as? GoActivity)?.close() }
        )
    }

    // ---------- Helpers ----------

    private fun color(@ColorRes id: Int): Int = ContextCompat.getColor(requireContext(), id)

    private fun dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()

    private fun rounded(@ColorRes fill: Int, @ColorRes stroke: Int?, radius: Int, strokeWidth: Int = dp(1) + dp(1) / 2) =
        GradientDrawable().apply {
            cornerRadius = radius.toFloat()
            setColor(color(fill))
            stroke?.let { setStroke(strokeWidth, color(it)) }
        }

    private companion object {
        const val ENGLISH_BADGE = "EN"
        const val LETTER_TEXT_SP = 22f
        const val USED_TILE_ALPHA = 0.35f
    }
}

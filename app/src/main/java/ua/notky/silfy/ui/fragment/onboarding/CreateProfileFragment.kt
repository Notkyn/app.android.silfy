package ua.notky.silfy.ui.fragment.onboarding

import android.content.res.Resources
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.ConfigurationCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ua.notky.base.extension.hideKeyboard
import ua.notky.base.extension.observe
import ua.notky.base.extension.openLink
import ua.notky.base.extension.openSafeScreen
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.R
import ua.notky.silfy.databinding.FragmentCreateProfileBinding
import ua.notky.silfy.databinding.ItemLanguageOptionBinding
import ua.notky.silfy.models.enums.AppLanguage
import ua.notky.silfy.models.states.OnboardingUiState
import ua.notky.silfy.ui.dialog.DialogTone
import ua.notky.silfy.ui.dialog.showSilfyDialog
import ua.notky.silfy.ui.view.applySystemBarsPadding
import ua.notky.silfy.ui.view.setLightSystemBars
import ua.notky.silfy.util.languageNameInUi
import ua.notky.silfy.viewmodel.onboarding.OnboardingViewModel

/** 1d Create profile: name + translation language (= UI language), then "Good to know" */
class CreateProfileFragment : BaseBindingFragment<FragmentCreateProfileBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCreateProfileBinding
        get() = FragmentCreateProfileBinding::inflate

    private val onboardingViewModel by activityViewModels<OnboardingViewModel>()

    private val languageRows = mutableMapOf<AppLanguage, ItemLanguageOptionBinding>()
    private var selectedLanguage: AppLanguage = systemLanguage()

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(onboardingViewModel)
            .build()
    }

    override fun initialize(savedInstanceState: Bundle?) {
        savedInstanceState?.getString(KEY_LANGUAGE)
            ?.let { AppLanguage.fromCode(it) }
            ?.let { selectedLanguage = it }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_LANGUAGE, selectedLanguage.code)
    }

    override fun onResume() {
        super.onResume()
        setLightSystemBars(true)
    }

    override fun initializeViews() {
        binding.root.applySystemBarsPadding(top = true)
        binding.bottomBar.applySystemBarsPadding(bottom = true)

        initLanguageRows()
        initPrivacyText()
        renderSelectedLanguage()
        renderCreateButton()
    }

    override fun initializeListeners() {
        binding.buttonBack.setOnClickListener { onBack() }
        binding.editName.doAfterTextChanged { renderCreateButton() }
        binding.editName.setOnEditorActionListener { view, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                view.clearFocus()
                view.hideKeyboard()
            }
            false
        }
        binding.buttonCreate.setOnClickListener { onCreateProfile() }
    }

    override fun initializeViewModels() {
        observe(onboardingViewModel.state, ::renderState)
    }

    private fun initLanguageRows() {
        val inflater = LayoutInflater.from(requireContext())

        AppLanguage.values().forEach { language ->
            val row = ItemLanguageOptionBinding.inflate(inflater, binding.listLanguages, false)
            row.textBadge.text = language.badge
            row.textName.text = languageLabel(language)
            row.root.setOnClickListener {
                selectedLanguage = language
                renderSelectedLanguage()
            }
            languageRows[language] = row
            binding.listLanguages.addView(row.root)
        }
    }

    /** "Українська Ukrainian": native name + name in the UI language (lighter), when they differ */
    private fun languageLabel(language: AppLanguage): CharSequence {
        val uiName = requireContext().languageNameInUi(language)
        if (uiName.equals(language.nativeName, ignoreCase = true)) return language.nativeName

        return SpannableStringBuilder(language.nativeName).apply {
            append(" ")
            val start = length
            append(uiName)
            val color = ContextCompat.getColor(requireContext(), R.color.text_tertiary)
            setSpan(ForegroundColorSpan(color), start, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            val regular = ResourcesCompat.getFont(requireContext(), R.font.onest_regular)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && regular != null) {
                setSpan(TypefaceSpan(regular), start, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
    }

    private fun renderSelectedLanguage() {
        languageRows.forEach { (language, row) ->
            val selected = language == selectedLanguage
            row.root.isActivated = selected
            row.radio.isChecked = selected
        }
    }

    /** "By continuing you accept the Privacy Policy", the policy is a link */
    private fun initPrivacyText() {
        val policy = getString(R.string.privacy_policy)
        val text = getString(R.string.create_privacy, policy)
        val start = text.indexOf(policy)

        binding.textPrivacy.text = SpannableStringBuilder(text).apply {
            if (start >= 0) {
                val end = start + policy.length
                setSpan(object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        openLink(getString(R.string.url_privacy_policy))
                    }

                    override fun updateDrawState(paint: TextPaint) {
                        paint.color = ContextCompat.getColor(requireContext(), R.color.ink)
                        paint.isUnderlineText = true
                    }
                }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                setSpan(StyleSpan(Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
        binding.textPrivacy.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun renderCreateButton() {
        val isCreating = onboardingViewModel.state.value == OnboardingUiState.Creating
        binding.buttonCreate.isEnabled = !isCreating && !binding.editName.text.isNullOrBlank()
    }

    private fun onCreateProfile() {
        binding.editName.hideKeyboard()
        onboardingViewModel.createProfile(binding.editName.text.toString(), selectedLanguage)
    }

    private fun renderState(state: OnboardingUiState?) {
        renderCreateButton()

        when (state) {
            is OnboardingUiState.ProfileCreated -> {
                onboardingViewModel.consumeState()
                openSafeScreen(
                    CreateProfileFragmentDirections.toFragmentGoodToKnow(state.language.code)
                        .setOnboarding(true)
                )
            }
            OnboardingUiState.Failure -> {
                onboardingViewModel.consumeState()
                showSilfyDialog(
                    icon = R.drawable.ic_lc_triangle_alert,
                    tone = DialogTone.WARNING,
                    title = getString(R.string.create_error_title),
                    message = getString(R.string.create_error_message),
                    cancelText = null
                )
            }
            else -> {}
        }
    }

    private fun onBack() {
        if (!findNavController().navigateUp()) requireActivity().finish()
    }

    private companion object {
        const val KEY_LANGUAGE = "language"

        /** System language when it is one of the 7, otherwise Ukrainian */
        fun systemLanguage(): AppLanguage {
            val system = ConfigurationCompat.getLocales(Resources.getSystem().configuration)[0]
            return AppLanguage.fromCode(system?.language) ?: AppLanguage.UK
        }
    }
}

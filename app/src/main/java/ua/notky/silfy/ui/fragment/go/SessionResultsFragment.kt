package ua.notky.silfy.ui.fragment.go

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import ua.notky.base.extension.observe
import ua.notky.base.extension.openSafeScreen
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.R
import ua.notky.silfy.databinding.FragmentSessionResultsBinding
import ua.notky.silfy.databinding.ItemSessionStatBinding
import ua.notky.silfy.models.enums.GoStatsType
import ua.notky.silfy.ui.activity.GoActivity
import ua.notky.silfy.ui.view.applySystemBarsPadding
import ua.notky.silfy.ui.view.setLightSystemBars
import ua.notky.silfy.viewmodel.go.SessionViewModel

/** 4j Results: why the session ended, accuracy, counters; "Another session" starts again with the same settings */
@AndroidEntryPoint
class SessionResultsFragment : BaseBindingFragment<FragmentSessionResultsBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSessionResultsBinding
        get() = FragmentSessionResultsBinding::inflate

    private val sessionViewModel by activityViewModels<SessionViewModel>()

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(sessionViewModel)
            .build()
    }

    override fun onResume() {
        super.onResume()
        setLightSystemBars(true)
    }

    override fun initializeViews() {
        binding.root.applySystemBarsPadding(top = true)
        binding.bottomBar.applySystemBarsPadding(bottom = true)

        setupStat(binding.statSelected, R.string.results_words_selected, null, R.color.text_secondary, R.color.ink)
        setupStat(binding.statUsed, R.string.results_words_used, null, R.color.text_secondary, R.color.ink)
        setupStat(binding.statCorrect, R.string.results_correct, R.color.success_bg, R.color.success_text_2, R.color.success_text)
        setupStat(binding.statWrong, R.string.results_wrong, R.color.error_bg, R.color.error_text_2, R.color.error_text)
    }

    override fun initializeListeners() {
        // Back from the results leaves the training, as "Back to dictionary" does
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) { backToDictionary() }

        binding.buttonAgain.setOnClickListener { openSafeScreen(R.id.action_results_to_session) }
        binding.buttonBack.setOnClickListener { backToDictionary() }
    }

    override fun initializeViewModels() {
        viewLifecycleOwner.observe(sessionViewModel.step, ::renderStep)
    }

    private fun renderStep(step: SessionViewModel.Step?) {
        val result = (step as? SessionViewModel.Step.Finished)?.result ?: return

        val (reason, icon) = when (result.reason) {
            GoStatsType.TIME -> R.string.results_time to R.drawable.ic_lc_timer_15
            GoStatsType.ERROR -> R.string.results_mistakes to R.drawable.ic_lc_heart_crack_15
            else -> R.string.results_ended to R.drawable.ic_lc_log_out_15
        }
        binding.textReason.setText(reason)
        TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds(binding.textReason, icon, 0, 0, 0)

        binding.textTitle.text = getString(R.string.results_title, result.profileName)
        binding.ringAccuracy.percent = result.accuracy
        binding.textAccuracy.text = getString(R.string.results_percent, result.accuracy)

        binding.statSelected.textValue.text = result.selected.toString()
        binding.statUsed.textValue.text = result.used.toString()
        binding.statCorrect.textValue.text = result.correct.toString()
        binding.statWrong.textValue.text = result.wrong.toString()
    }

    /** @param background null — white with a border */
    private fun setupStat(
        stat: ItemSessionStatBinding,
        @StringRes label: Int,
        @ColorRes background: Int?,
        @ColorRes labelColor: Int,
        @ColorRes valueColor: Int
    ) {
        stat.textLabel.setText(label)
        stat.textLabel.setTextColor(color(labelColor))
        stat.textValue.setTextColor(color(valueColor))
        if (background == null) {
            stat.root.setBackgroundResource(R.drawable.ds_bg_stat_tile_outlined)
        } else {
            stat.root.background?.mutate()?.setTint(color(background))
        }
    }

    private fun backToDictionary() {
        (activity as? GoActivity)?.backToDictionary()
    }

    private fun color(@ColorRes id: Int): Int = ContextCompat.getColor(requireContext(), id)
}

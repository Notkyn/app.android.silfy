package ua.notky.silfy.ui.fragment.goodtoknow

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import ua.notky.base.extension.startActivity
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.silfy.R
import ua.notky.silfy.databinding.FragmentGoodToKnowBinding
import ua.notky.silfy.models.enums.AppLanguage
import ua.notky.silfy.ui.activity.MainActivity
import ua.notky.silfy.ui.view.applySystemBarsPadding
import ua.notky.silfy.ui.view.setLightSystemBars

/**
 * 6e–6g Good to know. Right after a profile is created ([GoodToKnowFragmentArgs.onboarding]) it is the last
 * onboarding screen: the last page's button and "back" open the dictionary.
 */
class GoodToKnowFragment : BaseBindingFragment<FragmentGoodToKnowBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentGoodToKnowBinding
        get() = FragmentGoodToKnowBinding::inflate

    private val args by navArgs<GoodToKnowFragmentArgs>()

    private val dots = mutableListOf<View>()

    private val pageCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            renderControls(position)
        }
    }

    override fun initialize(savedInstanceState: Bundle?) {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) { onBack() }
    }

    override fun onResume() {
        super.onResume()
        setLightSystemBars(true)
    }

    override fun initializeViews() {
        binding.root.applySystemBarsPadding(top = true)
        binding.bottomBar.applySystemBarsPadding(bottom = true)

        val language = AppLanguage.fromCode(args.languageCode) ?: AppLanguage.UK
        binding.pager.adapter = GoodToKnowPagesAdapter(language)
        binding.pager.registerOnPageChangeCallback(pageCallback)

        initDots()
        renderControls(binding.pager.currentItem)
    }

    override fun initializeListeners() {
        binding.buttonBack.setOnClickListener { onBack() }
        binding.buttonPrevious.setOnClickListener {
            binding.pager.currentItem = binding.pager.currentItem - 1
        }
        binding.buttonNext.setOnClickListener {
            if (isLastPage() && args.onboarding) {
                finishOnboarding()
            } else {
                binding.pager.currentItem = binding.pager.currentItem + 1
            }
        }
    }

    override fun onDestroyView() {
        binding.pager.unregisterOnPageChangeCallback(pageCallback)
        dots.clear()
        super.onDestroyView()
    }

    private fun initDots() {
        val size = resources.getDimensionPixelSize(R.dimen.ds_pager_dot)
        val gap = resources.getDimensionPixelSize(R.dimen.ds_pager_dot_gap)

        repeat(binding.pager.adapter?.itemCount ?: 0) { index ->
            val dot = View(requireContext()).apply {
                background = ContextCompat.getDrawable(context, R.drawable.ds_bg_dot)
                layoutParams = LinearLayout.LayoutParams(size, size).apply {
                    if (index > 0) marginStart = gap
                }
            }
            dots += dot
            binding.dots.addView(dot)
        }
    }

    private fun renderControls(position: Int) {
        val last = isLastPage(position)

        binding.buttonPrevious.isEnabled = position > 0
        binding.buttonPrevious.alpha = if (position > 0) 1f else DISABLED_ALPHA

        val nextEnabled = !last || args.onboarding
        binding.buttonNext.isEnabled = nextEnabled
        binding.buttonNext.alpha = if (nextEnabled) 1f else DISABLED_ALPHA
        binding.buttonNext.setIconResource(
            if (last && args.onboarding) R.drawable.ic_lc_check else R.drawable.ic_lc_chevron_right
        )
        binding.buttonNext.contentDescription =
            getString(if (last && args.onboarding) R.string.gtk_done else R.string.gtk_next)

        val dotSize = resources.getDimensionPixelSize(R.dimen.ds_pager_dot)
        val activeWidth = resources.getDimensionPixelSize(R.dimen.ds_pager_dot_active)
        dots.forEachIndexed { index, dot ->
            val active = index == position
            dot.layoutParams = dot.layoutParams.apply { width = if (active) activeWidth else dotSize }
            dot.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(requireContext(), if (active) R.color.ink else R.color.control_border)
            )
        }
    }

    private fun isLastPage(position: Int = binding.pager.currentItem): Boolean {
        return position == (binding.pager.adapter?.itemCount ?: 0) - 1
    }

    private fun onBack() {
        if (args.onboarding) {
            finishOnboarding()
        } else {
            findNavController().navigateUp()
        }
    }

    private fun finishOnboarding() {
        activity?.startActivity<MainActivity>()
        activity?.finishAffinity()
    }

    private companion object {
        const val DISABLED_ALPHA = 0.35f
    }
}

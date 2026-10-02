package ua.notky.silfy.ui.view.segmented

import android.content.Context
import android.os.Build
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.view.children
import androidx.core.widget.TextViewCompat
import ua.notky.silfy.R

/**
 * Segmented control (Easy / Hard, 5 / 10 / 30 min, All / Favourites / Blacklist).
 * Container and its padding come from the style: Widget.Silfy.Segmented or Widget.Silfy.Segmented.Tabs.
 * Selected option is white with a soft shadow, the rest are transparent.
 */
class SegmentedControl @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val optionHeight = resources.getDimensionPixelSize(R.dimen.ds_segmented_option_height)
    private val optionGap = resources.getDimensionPixelSize(R.dimen.ds_segmented_padding)
    private val optionElevation = resources.displayMetrics.density * SELECTED_ELEVATION_DP
    private val colorSelected = ContextCompat.getColor(context, R.color.text_primary)
    private val colorDefault = ContextCompat.getColor(context, R.color.text_secondary)
    private val colorCount = ContextCompat.getColor(context, R.color.text_tertiary)
    private val countTextSize = resources.getDimensionPixelSize(R.dimen.ds_segmented_count_text)

    private var labels: List<CharSequence> = emptyList()
    private var counts: List<Int?> = emptyList()
    private var onOptionSelected: ((Int) -> Unit)? = null

    var selectedIndex: Int = 0
        set(value) {
            field = value
            renderSelection()
        }

    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
        clipToPadding = false
        clipChildren = false

        context.withStyledAttributes(attrs, R.styleable.SegmentedControl, defStyleAttr) {
            getTextArray(R.styleable.SegmentedControl_segmentedEntries)?.let { setOptions(it.toList()) }
            selectedIndex = getInt(R.styleable.SegmentedControl_segmentedSelected, 0)
        }
    }

    fun setOptions(options: List<CharSequence>) {
        labels = options
        removeAllViews()
        options.indices.forEach { index -> addView(createOption(index)) }
        renderTexts()
        renderSelection()
    }

    /** Counters shown after the label in a lighter color (dictionary tabs). `null` hides a counter. */
    fun setCounts(values: List<Int?>) {
        counts = values
        renderTexts()
    }

    fun setOnOptionSelectedListener(listener: ((Int) -> Unit)?) {
        onOptionSelected = listener
    }

    private fun createOption(index: Int): AppCompatTextView {
        return AppCompatTextView(context).apply {
            layoutParams = LayoutParams(0, optionHeight, 1f).apply {
                if (index > 0) marginStart = optionGap
            }
            gravity = Gravity.CENTER
            maxLines = 1
            includeFontPadding = false
            TextViewCompat.setTextAppearance(this, R.style.TextAppearance_Silfy_Button_Small)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                outlineSpotShadowColor = ContextCompat.getColor(context, R.color.ink)
                outlineAmbientShadowColor = ContextCompat.getColor(context, R.color.ink)
            }
            setOnClickListener {
                if (selectedIndex != index) {
                    selectedIndex = index
                    onOptionSelected?.invoke(index)
                }
            }
        }
    }

    private fun renderTexts() {
        children.forEachIndexed { index, view ->
            val label = labels.getOrNull(index) ?: return@forEachIndexed
            val count = counts.getOrNull(index)
            (view as AppCompatTextView).text = if (count == null) label else labelWithCount(label, count)
        }
    }

    private fun labelWithCount(label: CharSequence, count: Int): CharSequence {
        return SpannableStringBuilder(label).apply {
            append("  ")
            val start = length
            append(count.toString())
            setSpan(AbsoluteSizeSpan(countTextSize), start, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(ForegroundColorSpan(colorCount), start, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    private fun renderSelection() {
        children.forEachIndexed { index, view ->
            val selected = index == selectedIndex
            val option = view as AppCompatTextView
            option.isSelected = selected
            option.setTextColor(if (selected) colorSelected else colorDefault)
            option.background = if (selected) {
                ContextCompat.getDrawable(context, R.drawable.ds_bg_segmented_option)
            } else {
                null
            }
            option.elevation = if (selected) optionElevation else 0f
        }
    }

    private companion object {
        const val SELECTED_ELEVATION_DP = 1.5f
    }
}

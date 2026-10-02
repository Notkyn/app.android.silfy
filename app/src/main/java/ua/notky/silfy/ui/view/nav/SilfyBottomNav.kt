package ua.notky.silfy.ui.view.nav

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import ua.notky.silfy.R
import ua.notky.silfy.databinding.ItemBottomNavBinding
import ua.notky.silfy.databinding.ItemBottomNavStartBinding

/**
 * Floating bottom navigation: ink bar 68dp, radius 24, shadow.
 * Words · Categories · Start (aqua, no label) · Profile · Menu.
 * Margins (12dp sides, 14dp bottom) are set by the screen layout.
 */
class SilfyBottomNav @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    enum class Tab(@DrawableRes val icon: Int, @StringRes val label: Int) {
        WORDS(R.drawable.ic_lc_book_open, R.string.nav_words),
        CATEGORIES(R.drawable.ic_lc_layout_grid, R.string.nav_categories),
        PROFILE(R.drawable.ic_lc_user_round, R.string.nav_profile),
        MENU(R.drawable.ic_lc_menu, R.string.nav_menu)
    }

    private val items = mutableMapOf<Tab, ItemBottomNavBinding>()
    private var onTabClick: ((Tab) -> Unit)? = null
    private var onStartClick: (() -> Unit)? = null

    var selectedTab: Tab? = null
        set(value) {
            field = value
            items.forEach { (tab, binding) -> binding.item.isSelected = tab == value }
        }

    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
        val paddingHorizontal = (NAV_PADDING_DP * resources.displayMetrics.density).toInt()
        setPadding(paddingHorizontal, 0, paddingHorizontal, 0)
        background = ContextCompat.getDrawable(context, R.drawable.ds_bg_nav)
        elevation = resources.getDimension(R.dimen.ds_elevation_nav)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            outlineSpotShadowColor = ContextCompat.getColor(context, R.color.ink)
            outlineAmbientShadowColor = ContextCompat.getColor(context, R.color.ink)
        }

        val inflater = LayoutInflater.from(context)
        addTab(inflater, Tab.WORDS)
        addTab(inflater, Tab.CATEGORIES)
        addStart(inflater)
        addTab(inflater, Tab.PROFILE)
        addTab(inflater, Tab.MENU)

        context.withStyledAttributes(attrs, R.styleable.SilfyBottomNav, defStyleAttr) {
            if (hasValue(R.styleable.SilfyBottomNav_navSelected)) {
                selectedTab = Tab.values()[getInt(R.styleable.SilfyBottomNav_navSelected, 0)]
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val height = MeasureSpec.makeMeasureSpec(
            resources.getDimensionPixelSize(R.dimen.ds_nav_height),
            MeasureSpec.EXACTLY
        )
        super.onMeasure(widthMeasureSpec, height)
    }

    /** Called on every tab tap, including the already selected one. */
    fun setOnTabClickListener(listener: ((Tab) -> Unit)?) {
        onTabClick = listener
    }

    fun setOnStartClickListener(listener: (() -> Unit)?) {
        onStartClick = listener
    }

    private fun addTab(inflater: LayoutInflater, tab: Tab) {
        val binding = ItemBottomNavBinding.inflate(inflater, this, false)
        binding.icon.setImageResource(tab.icon)
        binding.label.setText(tab.label)
        binding.item.contentDescription = context.getString(tab.label)
        binding.item.setOnClickListener {
            selectedTab = tab
            onTabClick?.invoke(tab)
        }
        items[tab] = binding
        addView(binding.root)
    }

    private fun addStart(inflater: LayoutInflater) {
        val binding = ItemBottomNavStartBinding.inflate(inflater, this, false)
        binding.buttonStart.setOnClickListener { onStartClick?.invoke() }
        addView(binding.root)
    }

    private companion object {
        const val NAV_PADDING_DP = 6
    }
}

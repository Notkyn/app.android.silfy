package ua.notky.base.ui.layout.frame

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import ua.notky.base.ui.layout.BaseInitializationLayout

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 11.05.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
abstract class BaseFrameLayout : FrameLayout, BaseInitializationLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initialize(attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int,
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        initialize(attrs)
    }

    private fun initialize(attrs: AttributeSet?) {
        initializeBinding()
        initializeStyleable(attrs, setStyleable())
        init(attrs)
        initializeViews()
        initializeListeners()
    }

    open fun setStyleable(): IntArray? {
        return null
    }

    private fun initializeStyleable(
        attrs: AttributeSet?,
        resIdStyleable: IntArray?
    ) {
        resIdStyleable?.let {
            val typedArray = context.theme.obtainStyledAttributes(
                attrs, it, 0, 0
            )
            try {
                setStyleableValue(typedArray)
            } finally {
                typedArray.recycle()
            }
        }
    }
}
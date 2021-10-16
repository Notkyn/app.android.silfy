package ua.notky.base.ui.view.phone

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.InputType
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import timber.log.Timber
import ua.notky.base.R
import ua.notky.base.extension.toDp

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

class CountryCodeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {
    private var parentHeightAttr = WRAP_CONTENT
    private val metric = resources.displayMetrics
    private var textSizeAttr =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, TEXT_SIZE_DEF, metric)
    private var textColor: Int = Color.BLACK
    private var spinnerIcon: Drawable? = null

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CountryCodeView)

        // Dimensions
        parentHeightAttr = getLayoutDimension(
            typedArray, R.styleable.CountryCodeView_android_layout_height
        )

        textSizeAttr = typedArray.getDimension(
            R.styleable.CountryCodeView_android_textSize, textSizeAttr
        )

        // Drawable
        spinnerIcon = typedArray.getDrawable(R.styleable.CountryCodeView_spinner_icon)

        // Colors
        textColor = typedArray.getColor(
            R.styleable.CountryCodeView_android_textColor, Color.BLACK
        )

        setPadding(paddingStart, paddingTop, paddingEnd, paddingBottom)
        typedArray.recycle()
        addViews()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMeasureSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec)

        val heightParent = when (parentHeightAttr) {
            MATCH_PARENT -> heightMeasureSize
            WRAP_CONTENT -> textSizeAttr.toInt() + paddingTop + paddingBottom
            0 -> textSizeAttr.toInt() + paddingTop + paddingBottom
            else -> parentHeightAttr
        }
        val heightView = heightParent - paddingTop - paddingBottom
        val widthParentWithoutPadding = widthMeasureSize - paddingStart - paddingEnd

        val logoImgView = getChildAt(0)
        val widthLogoImgView = (widthParentWithoutPadding * 0.10).toInt()
        logoImgView.measure(
            MeasureSpec.makeMeasureSpec(widthLogoImgView, widthMeasureMode),
            MeasureSpec.makeMeasureSpec(heightView, heightMeasureMode)
        )

        val codeTextView = getChildAt(1)
        val widthCodeTextView = (widthParentWithoutPadding * 0.15).toInt()
        codeTextView.measure(
            MeasureSpec.makeMeasureSpec(widthCodeTextView, widthMeasureMode),
            MeasureSpec.makeMeasureSpec(heightView, heightMeasureMode)
        )

        val arrowImgView = getChildAt(2)
        val widthArrowImgView = (widthParentWithoutPadding * 0.05).toInt()
        arrowImgView.measure(
            MeasureSpec.makeMeasureSpec(widthArrowImgView, widthMeasureMode),
            MeasureSpec.makeMeasureSpec(heightView, heightMeasureMode)
        )

        val phoneEditTextView = getChildAt(3)
        val widthPhoneEditTextView = (widthParentWithoutPadding * 0.7).toInt()
        phoneEditTextView.measure(
            MeasureSpec.makeMeasureSpec(widthPhoneEditTextView, widthMeasureMode),
            MeasureSpec.makeMeasureSpec(heightView, heightMeasureMode)
        )

        setMeasuredDimension(
            widthMeasureSpec,
            MeasureSpec.makeMeasureSpec(heightParent, heightMeasureMode)
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val logoImgView = getChildAt(0)
        val leftLogo = paddingStart
        val rightLogo = logoImgView.measuredWidth + leftLogo
        val bottomLogo = logoImgView.measuredHeight + paddingTop
        logoImgView.layout(
            leftLogo,
            paddingTop,
            rightLogo,
            bottomLogo
        )

        val codeTextView = getChildAt(1)
        val rightCode = codeTextView.measuredWidth + rightLogo
        val bottomCode = codeTextView.measuredHeight + paddingTop
        codeTextView.layout(
            rightLogo,
            paddingTop,
            rightCode,
            bottomCode
        )

        val arrowImgView = getChildAt(2)
        val rightArrow = arrowImgView.measuredWidth + rightCode
        val bottomArrow = arrowImgView.measuredHeight + paddingTop
        arrowImgView.layout(
            rightCode,
            paddingTop,
            rightArrow,
            bottomArrow
        )

        val phoneEditTextView = getChildAt(3)
        val rightPhone = measuredWidth - paddingEnd
        val bottomPhone = phoneEditTextView.measuredHeight + paddingTop
        phoneEditTextView.layout(
            rightArrow,
            paddingTop,
            rightPhone,
            bottomPhone
        )
    }

    private fun addViews() {
        // image view - country logo
        val logoImgView = AppCompatImageView(context)
        logoImgView.setBackgroundColor(Color.YELLOW)
        logoImgView.isClickable = true
        logoImgView.setOnClickListener {
            log("In Code Listener")
        }

        // text view - code country
        val codeTextView = AppCompatTextView(context)
        // todo text for test
        codeTextView.text = "+589-5"
        codeTextView.setBackgroundColor(Color.GREEN)
        codeTextView.textSize = textSizeAttr.toDp()
        codeTextView.setTextColor(textColor)
        codeTextView.gravity = Gravity.CENTER
        codeTextView.isClickable = true
        codeTextView.setOnClickListener(CodeClickListener())

        // image view - arrow down
        val arrowImgView = AppCompatImageView(context)
        arrowImgView.setBackgroundColor(Color.BLUE)
        spinnerIcon?.let { arrowImgView.setImageDrawable(spinnerIcon) }
        arrowImgView.scaleType = ImageView.ScaleType.CENTER_INSIDE
        arrowImgView.isClickable = true
        arrowImgView.setOnClickListener(CodeClickListener())

        // edit text view - phone number
        val phoneEditTextView = AppCompatEditText(context)
        phoneEditTextView.setBackgroundColor(Color.RED)
        phoneEditTextView.textSize = textSizeAttr.toDp()
        phoneEditTextView.setTextColor(textColor)
        // todo text for test
        phoneEditTextView.setText("2548455564")
        phoneEditTextView.setPadding(
            PADDING_HORIZONTAL_PX_DEF.toDp(),
            0,
            PADDING_HORIZONTAL_PX_DEF.toDp(),
            0
        )
        phoneEditTextView.gravity = Gravity.CENTER_VERTICAL
        phoneEditTextView.inputType = InputType.TYPE_CLASS_NUMBER
        phoneEditTextView.imeOptions = EditorInfo.IME_ACTION_DONE

        addView(logoImgView)
        addView(codeTextView)
        addView(arrowImgView)
        addView(phoneEditTextView)
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        super.setPadding(
            left,
            top,
            right + PADDING_HORIZONTAL_PX_DEF.toDp(),
            bottom
        )
    }

    private fun getLayoutDimension(typeArray: TypedArray, indexStyle: Int): Int {
        return when (typeArray.getString(indexStyle)) {
            MATCH_PARENT.toString() -> MATCH_PARENT
            WRAP_CONTENT.toString() -> WRAP_CONTENT
            else -> typeArray.getDimensionPixelSize(indexStyle, WRAP_CONTENT)
        }
    }

    private fun log(msg: String) {
        Timber.i(msg)
    }

    companion object {
        const val MATCH_PARENT = -1
        const val WRAP_CONTENT = -2
        const val PADDING_HORIZONTAL_PX_DEF = 100
        const val TEXT_SIZE_DEF = 15f
    }

    inner class CodeClickListener : OnClickListener {
        override fun onClick(view: View?) {

            log("In Listener")
            log(view.toString())
        }
    }
}

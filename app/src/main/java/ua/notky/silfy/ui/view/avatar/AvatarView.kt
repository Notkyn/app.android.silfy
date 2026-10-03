package ua.notky.silfy.ui.view.avatar

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.TextViewCompat
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import ua.notky.silfy.R
import ua.notky.silfy.models.model.Profile

/**
 * Profile avatar: the first letter of the name (Unbounded) on one of R.array.avatar_colors,
 * or the profile photo cropped to a circle. Size comes from the layout.
 */
class AvatarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val initial = AppCompatTextView(context).apply {
        gravity = Gravity.CENTER
        includeFontPadding = false
        maxLines = 1
        TextViewCompat.setTextAppearance(this, R.style.TextAppearance_Silfy_Initial)
    }

    private val photo = AppCompatImageView(context).apply {
        scaleType = ImageView.ScaleType.CENTER_CROP
        isVisible = false
    }

    init {
        background = ContextCompat.getDrawable(context, R.drawable.ds_bg_oval)
        addView(initial, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
        addView(photo, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
        importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_NO
    }

    fun setProfile(profile: Profile) {
        setAvatar(profile.name, profile.avatarColor, profile.photo)
    }

    fun setAvatar(name: String, colorIndex: Int, photoUri: String? = null) {
        initial.text = initialOf(name)
        backgroundTintList = ColorStateList.valueOf(avatarColor(context, colorIndex))

        photo.isVisible = !photoUri.isNullOrEmpty()
        if (!photoUri.isNullOrEmpty()) {
            Picasso.get()
                .load(photoUri)
                .transform(CropCircleTransformation())
                .into(photo)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // 19sp on a 48dp avatar, 26sp on 64dp
        initial.setTextSize(TypedValue.COMPLEX_UNIT_PX, h * INITIAL_TEXT_RATIO)
    }

    companion object {
        private const val INITIAL_TEXT_RATIO = 0.4f

        fun initialOf(name: String): String {
            val trimmed = name.trim()
            if (trimmed.isEmpty()) return ""
            val end = trimmed.offsetByCodePoints(0, 1)
            return trimmed.substring(0, end).uppercase()
        }

        fun avatarColor(context: Context, index: Int): Int {
            val colors = context.resources.obtainTypedArray(R.array.avatar_colors)
            return try {
                colors.getColor(Math.floorMod(index, colors.length()), 0)
            } finally {
                colors.recycle()
            }
        }
    }
}

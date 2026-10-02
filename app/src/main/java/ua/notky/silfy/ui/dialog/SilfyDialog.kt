package ua.notky.silfy.ui.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import ua.notky.silfy.R
import ua.notky.silfy.databinding.DialogSilfyBinding

/**
 * Silfy 2.0 dialog (replaces AlertDialog): icon tile → title → text → Cancel / OK.
 */
enum class DialogTone(@ColorRes val tileColor: Int, @ColorRes val iconColor: Int) {
    /** Reset progress, end session, clear blacklist  */
    NEUTRAL(R.color.seg_bg_light, R.color.ink),

    /** Delete word / category / profile */
    DANGER(R.color.error_bg, R.color.error_border),

    /** Restore defaults, clear favourites, no words match */
    WARNING(R.color.fav_bg, R.color.warning_icon)
}

/**
 * @param cancelText `null` hides the Cancel button (single-button dialog).
 * @param destructive OK button becomes red (#E5484D) instead of ink.
 */
fun showSilfyDialog(
    context: Context,
    @DrawableRes icon: Int,
    tone: DialogTone,
    title: CharSequence,
    message: CharSequence? = null,
    okText: CharSequence = context.getString(R.string.dialog_button_ok),
    cancelText: CharSequence? = context.getString(R.string.dialog_button_cancel),
    destructive: Boolean = false,
    onOk: (() -> Unit)? = null,
    onCancel: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null
): Dialog {
    val dialog = AppCompatDialog(context, R.style.ThemeOverlay_Silfy_V2_Dialog)
    val binding = DialogSilfyBinding.inflate(LayoutInflater.from(dialog.context))

    binding.iconTile.backgroundTintList = ContextCompat.getColorStateList(context, tone.tileColor)
    binding.icon.setImageResource(icon)
    ImageViewCompat.setImageTintList(
        binding.icon,
        ColorStateList.valueOf(ContextCompat.getColor(context, tone.iconColor))
    )

    binding.textTitle.text = title
    binding.textMessage.text = message
    binding.textMessage.isVisible = !message.isNullOrEmpty()

    binding.buttonOk.text = okText
    binding.buttonOk.backgroundTintList = ContextCompat.getColorStateList(
        context,
        if (destructive) R.color.ds_btn_destructive_bg else R.color.ds_btn_primary_bg
    )
    binding.buttonOk.setOnClickListener {
        onOk?.invoke()
        dialog.dismiss()
    }

    binding.buttonCancel.text = cancelText
    binding.buttonCancel.isVisible = cancelText != null
    binding.buttonCancel.setOnClickListener { dialog.cancel() }

    dialog.setContentView(binding.root)
    dialog.setCanceledOnTouchOutside(true)
    dialog.setOnCancelListener { onCancel?.invoke() }
    dialog.setOnDismissListener { onDismiss?.invoke() }

    val margin = context.resources.getDimensionPixelSize(R.dimen.ds_form_padding)
    dialog.window?.apply {
        setLayout(
            context.resources.displayMetrics.widthPixels - 2 * margin,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        setGravity(Gravity.CENTER)
    }

    dialog.show()
    return dialog
}

fun Fragment.showSilfyDialog(
    @DrawableRes icon: Int,
    tone: DialogTone,
    title: CharSequence,
    message: CharSequence? = null,
    okText: CharSequence = getString(R.string.dialog_button_ok),
    cancelText: CharSequence? = getString(R.string.dialog_button_cancel),
    destructive: Boolean = false,
    onOk: (() -> Unit)? = null,
    onCancel: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null
): Dialog = showSilfyDialog(
    requireContext(), icon, tone, title, message, okText, cancelText,
    destructive, onOk, onCancel, onDismiss
)

fun Activity.showSilfyDialog(
    @DrawableRes icon: Int,
    tone: DialogTone,
    title: CharSequence,
    message: CharSequence? = null,
    okText: CharSequence = getString(R.string.dialog_button_ok),
    cancelText: CharSequence? = getString(R.string.dialog_button_cancel),
    destructive: Boolean = false,
    onOk: (() -> Unit)? = null,
    onCancel: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null
): Dialog = showSilfyDialog(
    this, icon, tone, title, message, okText, cancelText,
    destructive, onOk, onCancel, onDismiss
)

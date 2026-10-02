package ua.notky.silfy.ui.dialog

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ua.notky.base.ui.dialog.bottomsheet.BaseBindingBottomSheetDialogFragment
import ua.notky.silfy.R

/**
 * Silfy 2.0 bottom sheet: white, top radius 28, scrim 45%, opens fully expanded.
 * Content root uses style Widget.Silfy.Sheet; first child — a View with Widget.Silfy.Sheet.Handle,
 * then a title with Widget.Silfy.Text.SheetTitle.
 */
abstract class BaseSilfyBottomSheet<VDB : ViewBinding> : BaseBindingBottomSheetDialogFragment<VDB>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.ThemeOverlay_Silfy_V2_BottomSheetDialog)
        setDialogState(BottomSheetBehavior.STATE_EXPANDED)
    }
}

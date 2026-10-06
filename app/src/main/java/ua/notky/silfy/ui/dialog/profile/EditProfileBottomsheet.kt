package ua.notky.silfy.ui.dialog.profile

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.google.android.flexbox.FlexboxLayout
import dagger.hilt.android.AndroidEntryPoint
import ua.notky.base.extension.hideKeyboard
import ua.notky.base.extension.observe
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.R
import ua.notky.silfy.databinding.BottomsheetEditProfileBinding
import ua.notky.silfy.models.model.EditProfileForm
import ua.notky.silfy.models.states.UpdateProfileUiState
import ua.notky.silfy.ui.dialog.BaseSilfyBottomSheet
import ua.notky.silfy.ui.dialog.DialogTone
import ua.notky.silfy.ui.dialog.showSilfyDialog
import ua.notky.silfy.ui.view.avatar.AvatarView
import ua.notky.silfy.viewmodel.profile.EditProfileViewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 22.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

/**
 * 5b Edit profile: the avatar preview follows the name, the color and the picked photo; "Save" stores
 * everything at once and closes. The profile screen updates by itself (Room).
 */
@AndroidEntryPoint
class EditProfileBottomsheet : BaseSilfyBottomSheet<BottomsheetEditProfileBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BottomsheetEditProfileBinding
        get() = BottomsheetEditProfileBinding::inflate

    private val editProfileViewModel by viewModels<EditProfileViewModel>()

    private val swatches = mutableListOf<View>()
    private lateinit var cameraButton: FrameLayout

    /** The loaded name goes into the field once; after that the field is the source */
    private var isNameShown = false

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { editProfileViewModel.selectPhoto(it.toString()) }
    }

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(editProfileViewModel)
            .build()
    }

    override fun initialize(savedInstanceState: Bundle?) {
        isNameShown = savedInstanceState != null
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    override fun initializeViews() {
        initSwatches()
    }

    override fun initializeListeners() {
        binding.editName.doAfterTextChanged { editProfileViewModel.setName(it?.toString().orEmpty()) }
        binding.editName.setOnEditorActionListener { view, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                view.clearFocus()
                view.hideKeyboard()
            }
            false
        }
        binding.buttonSave.setOnClickListener {
            binding.editName.hideKeyboard()
            editProfileViewModel.save()
        }
    }

    override fun initializeViewModels() {
        viewLifecycleOwner.observe(editProfileViewModel.form, ::renderForm)
        viewLifecycleOwner.observe(editProfileViewModel.uiState, ::renderUiState)
    }

    /** 5 color circles 34dp and the camera button */
    private fun initSwatches() {
        val size = resources.getDimensionPixelSize(R.dimen.ds_swatch)
        val gap = resources.getDimensionPixelSize(R.dimen.ds_swatch_gap)

        fun params() = FlexboxLayout.LayoutParams(size, size).apply { setMargins(0, 0, gap, gap) }

        repeat(SWATCH_COUNT) { index ->
            val swatch = View(requireContext()).apply {
                contentDescription = getString(R.string.profile_color, index + 1)
                isClickable = true
                isFocusable = true
                setOnClickListener { editProfileViewModel.selectColor(index) }
            }
            swatches.add(swatch)
            binding.swatches.addView(swatch, params())
        }

        cameraButton = FrameLayout(requireContext()).apply {
            contentDescription = getString(R.string.profile_photo)
            isClickable = true
            isFocusable = true
            setOnClickListener { pickImage.launch(MEDIA_TYPE_IMAGE) }

            val iconSize = resources.getDimensionPixelSize(R.dimen.ds_swatch_icon)
            addView(
                AppCompatImageView(context).apply {
                    setImageResource(R.drawable.ic_lc_camera)
                    setColorFilter(ContextCompat.getColor(context, R.color.ink))
                    importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO
                },
                FrameLayout.LayoutParams(iconSize, iconSize, Gravity.CENTER)
            )
        }
        binding.swatches.addView(cameraButton, params())
    }

    private fun renderForm(form: EditProfileForm?) {
        form ?: return

        if (!isNameShown) {
            isNameShown = true
            binding.editName.setText(form.name)
            binding.editName.setSelection(binding.editName.length())
        }

        binding.avatar.setAvatar(form.name, form.colorIndex, form.photo)

        val hasPhoto = form.photo != null
        swatches.forEachIndexed { index, swatch ->
            val selected = !hasPhoto && index == form.colorIndex
            swatch.background = circle(AvatarView.avatarColor(requireContext(), index), selected)
            swatch.isSelected = selected
        }
        cameraButton.background = circle(ContextCompat.getColor(requireContext(), R.color.seg_bg_light), hasPhoto)
        cameraButton.isSelected = hasPhoto

        renderSaveButton()
    }

    private fun circle(color: Int, selected: Boolean): GradientDrawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setColor(color)
            if (selected) {
                setStroke(resources.getDimensionPixelSize(R.dimen.ds_swatch_ring), ContextCompat.getColor(requireContext(), R.color.ink))
            }
        }
    }

    private fun renderSaveButton() {
        val isValid = editProfileViewModel.form.value?.isValid == true
        binding.buttonSave.isEnabled = isValid && editProfileViewModel.uiState.value == UpdateProfileUiState.Idle
    }

    private fun renderUiState(state: UpdateProfileUiState?) {
        renderSaveButton()

        when (state) {
            UpdateProfileUiState.Saved -> dismiss()
            UpdateProfileUiState.Failure -> {
                editProfileViewModel.consumeState()
                showSilfyDialog(
                    icon = R.drawable.ic_lc_triangle_alert,
                    tone = DialogTone.WARNING,
                    title = getString(R.string.profile_error_save),
                    message = getString(R.string.create_error_message),
                    cancelText = null
                )
            }
            else -> {}
        }
    }

    companion object {
        private const val TAG = "EditProfileBottomsheet"
        private const val MEDIA_TYPE_IMAGE = "image/*"

        /** The design shows the first 5 of R.array.avatar_colors */
        private const val SWATCH_COUNT = 5

        fun show(fragmentManager: FragmentManager) {
            if (fragmentManager.findFragmentByTag(TAG) != null) return
            EditProfileBottomsheet().show(fragmentManager, TAG)
        }
    }
}

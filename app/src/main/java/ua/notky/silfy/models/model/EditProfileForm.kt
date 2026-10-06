package ua.notky.silfy.models.model

import ua.notky.silfy.usecase.profile.UpdateProfileUseCase.PhotoChange

/**
 * 5b Edit profile sheet.
 *
 * @param colorIndex index in R.array.avatar_colors
 * @param photo what the avatar shows: saved photo (`file://`), picked image (`content://`) or `null` — the letter
 * @param photoChange what "Save" does with the photo
 */
data class EditProfileForm(
    val name: String,
    val colorIndex: Int,
    val photo: String?,
    val photoChange: PhotoChange = PhotoChange.Keep
) {
    val isValid: Boolean get() = name.isNotBlank()
}

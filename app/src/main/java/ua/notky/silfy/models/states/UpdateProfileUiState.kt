package ua.notky.silfy.models.states

/** 5b Edit profile: saving the sheet */
sealed class UpdateProfileUiState {
    object Idle : UpdateProfileUiState()
    object Saving : UpdateProfileUiState()
    object Saved : UpdateProfileUiState()
    object Failure : UpdateProfileUiState()
}

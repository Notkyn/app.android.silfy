package ua.notky.silfy.models.states

import ua.notky.silfy.models.enums.AppLanguage

sealed class OnboardingUiState {
    object Idle : OnboardingUiState()
    object Creating : OnboardingUiState()
    object ProfileSelected : OnboardingUiState()
    data class ProfileCreated(val language: AppLanguage) : OnboardingUiState()
    object Failure : OnboardingUiState()
}

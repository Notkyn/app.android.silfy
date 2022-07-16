package ua.notky.silfy.models.states

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

sealed class UpdateProfileUiState {
    object Checking : UpdateProfileUiState()
    object Updating : UpdateProfileUiState()
    object Updated : UpdateProfileUiState()
    sealed class Failure : UpdateProfileUiState() {
        object UpdateData : Failure()
        object UpdatePhoto : Failure()
    }
}
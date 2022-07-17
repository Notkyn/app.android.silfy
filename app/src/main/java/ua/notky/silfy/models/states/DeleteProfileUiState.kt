package ua.notky.silfy.models.states

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
sealed class DeleteProfileUiState {
    object Deleting : DeleteProfileUiState()
    object Deleted : DeleteProfileUiState()
    object LogOut : DeleteProfileUiState()
    object Failure : DeleteProfileUiState()
}

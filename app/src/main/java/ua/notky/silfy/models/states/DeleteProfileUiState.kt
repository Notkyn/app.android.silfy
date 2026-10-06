package ua.notky.silfy.models.states

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

/** 5d Delete profile. [Deleted] — the active profile is gone, the app goes to "Who's learning" */
sealed class DeleteProfileUiState {
    object Idle : DeleteProfileUiState()
    object Deleting : DeleteProfileUiState()
    object Deleted : DeleteProfileUiState()
    object Failure : DeleteProfileUiState()
}

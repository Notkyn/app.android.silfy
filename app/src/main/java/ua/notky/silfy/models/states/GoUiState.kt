package ua.notky.silfy.models.states

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

sealed class GoUiState {
    object Loading : GoUiState()
    object Loaded : GoUiState()
    object Failure : GoUiState()
    object Normal : GoUiState()
}
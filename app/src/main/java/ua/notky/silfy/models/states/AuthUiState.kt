package ua.notky.silfy.models.states

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

sealed class AuthUiState {
    object Loading : AuthUiState()
    object Loaded : AuthUiState()
    object Create : AuthUiState()
    object Created : AuthUiState()
    sealed class Failure : AuthUiState() {
        object Missing : Failure()
        object ErrorCheck : Failure()
        object ErrorCreate : Failure()
    }
}
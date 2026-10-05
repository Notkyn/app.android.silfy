package ua.notky.silfy.models.states

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
sealed class CategorySaveUiState {
    object Idle : CategorySaveUiState()
    object Saving : CategorySaveUiState()
    object Saved : CategorySaveUiState()
    object Failure : CategorySaveUiState()
}

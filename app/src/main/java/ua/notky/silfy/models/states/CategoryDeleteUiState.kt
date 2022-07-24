package ua.notky.silfy.models.states

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
sealed class CategoryDeleteUiState {
    object Normal : CategoryDeleteUiState()
    object Deleting : CategoryDeleteUiState()
    object Deleted : CategoryDeleteUiState()
    object Failure : CategoryDeleteUiState()
}

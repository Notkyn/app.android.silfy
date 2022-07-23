package ua.notky.silfy.models.states

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 23.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
sealed class EditWordUiState {
    object Saved : EditWordUiState()
    object Saving : EditWordUiState()
    sealed class Failure : EditWordUiState() {
        object Load : Failure()
        object Save : Failure()
    }
}

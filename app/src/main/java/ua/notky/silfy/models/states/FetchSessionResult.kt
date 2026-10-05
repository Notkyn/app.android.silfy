package ua.notky.silfy.models.states

import ua.notky.silfy.models.model.TrainingSession
import ua.notky.silfy.models.model.Word

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
sealed class FetchSessionResult {
    /** @param words the session pool; @param dictionary all words of the profile */
    data class Success(
        val session: TrainingSession,
        val words: List<Word>,
        val dictionary: List<Word>
    ) : FetchSessionResult()

    /** No word matches the settings */
    object Empty : FetchSessionResult()

    data class Failure(val error: Throwable?) : FetchSessionResult()
}

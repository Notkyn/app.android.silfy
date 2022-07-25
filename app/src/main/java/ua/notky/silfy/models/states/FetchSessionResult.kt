package ua.notky.silfy.models.states

import ua.notky.silfy.models.model.TrainingSession
import ua.notky.silfy.models.model.Word

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

sealed class FetchSessionResult {
    data class Success(val session: TrainingSession, val words: List<Word>) : FetchSessionResult()
    data class Failure(val error: Throwable?) : FetchSessionResult()
}
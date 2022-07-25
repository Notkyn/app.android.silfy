package ua.notky.silfy.mapper.session

import ua.notky.silfy.models.local.SessionStatsLocal
import ua.notky.silfy.models.model.TrainingSession

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 25.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
object SessionStatsLocalMapper {

    fun map(input: Any, params: Params): SessionStatsLocal {
        return when (input) {
            is TrainingSession -> map(input, params.userId)
            else -> throw IllegalStateException("Not be cast ${input::class.java.simpleName} to SessionStatsLocal")
        }
    }

    data class Params(
        val userId: Int
    )

    private fun map(input: TrainingSession, userId: Int): SessionStatsLocal {
        return SessionStatsLocal(
            input.id,
            input.startTime,
            input.updateTime,
            input.finishReasonType.id,
            input.categoryIds,
            input.wordIds,
            input.resultMap,
            userId
        )
    }
}
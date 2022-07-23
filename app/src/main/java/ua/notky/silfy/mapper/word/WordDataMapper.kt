package ua.notky.silfy.mapper.word

import ua.notky.silfy.models.dto.WordDto
import ua.notky.silfy.models.local.WordData
import ua.notky.silfy.models.states.WordState

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

object WordDataMapper {

    fun map(input: Any, params: Params): WordData {
        return when (input) {
            is WordDto -> map(input, params.userId)
            else -> throw IllegalStateException("Not be cast ${input::class.java.simpleName} to WordData")
        }
    }

    fun map(input: List<Any>, params: Params): List<WordData> {
        return input.map { map(it, params) }
    }

    data class Params(
        val userId: Int
    )

    private fun map(input: WordDto, userId: Int): WordData {
        return WordData(
            null,
            input.en,
            input.ua,
            WordState.UNKNOWN.id,
            isFavourite = false,
            isBlacklist = false,
            userId = userId
        )
    }
}
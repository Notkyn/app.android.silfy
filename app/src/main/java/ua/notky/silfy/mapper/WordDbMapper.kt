package ua.notky.silfy.mapper

import ua.notky.silfy.models.dto.WordDto
import ua.notky.silfy.models.local.WordDb
import ua.notky.silfy.models.states.WordState
import kotlin.random.Random

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

object WordDbMapper {

    fun map(input: Any, params: Params): WordDb {
        return when (input) {
            is WordDto -> map(input, params.userId)
            else -> throw IllegalStateException("Not be cast ${input::class.java.simpleName} to WordDb")
        }
    }

    fun map(input: List<Any>, params: Params): List<WordDb> {
        return input.map { map(it, params) }
    }

    data class Params(
        val userId: Int
    )

    private fun map(input: WordDto, userId: Int): WordDb {
        // todo for test
//        return WordDb(
//            null,
//            input.en,
//            input.ua,
//            WordState.UNKNOWN.id,
//            isFavourite = false,
//            isBlacklist = false,
//            userId = userId
//        )

        return WordDb(
            null,
            input.en,
            input.ua,
            WordState.getStateById(Random.nextInt(5)).id,
            isFavourite = Random.nextBoolean(),
            isBlacklist = Random.nextBoolean(),
            userId = userId
        )
    }
}
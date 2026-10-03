package ua.notky.silfy.mapper.word

import ua.notky.silfy.models.dto.WordDto
import ua.notky.silfy.models.enums.AppLanguage
import ua.notky.silfy.models.local.WordLocal
import ua.notky.silfy.models.model.Word
import ua.notky.silfy.models.states.WordState

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

object WordLocalMapper {

    fun map(input: Any, params: Params): WordLocal {
        return when (input) {
            is WordDto -> map(input, params.userId, requireNotNull(params.language))
            is Word -> map(input, params.userId)
            else -> throw IllegalStateException("Not be cast ${input::class.java.simpleName} to WordLocal")
        }
    }

    fun map(input: List<Any>, params: Params): List<WordLocal> {
        return input.map { map(it, params) }
    }

    /** @param language required for [WordDto]: picks the translation from the default dictionary */
    data class Params(
        val userId: Int,
        val language: AppLanguage? = null
    )

    private fun map(input: WordDto, userId: Int, language: AppLanguage): WordLocal {
        val translation = input.translation(language)
            ?: throw IllegalStateException("Word ${input.en} has no ${language.code} translation")

        return WordLocal(
            null,
            input.en.trim(),
            translation,
            WordState.UNKNOWN.id,
            WordState.UNKNOWN.minCount,
            isFavourite = false,
            isBlacklist = false,
            userId = userId
        )
    }

    private fun map(input: Word, userId: Int): WordLocal {
        return WordLocal(
            input.id,
            input.en.trim(),
            input.translation.trim(),
            WordState.getByCount(input.minCountState).id,
            input.minCountState,
            input.isFavourite,
            input.isBlacklist,
            userId
        )
    }
}
package ua.notky.silfy.mapper

import ua.notky.silfy.models.local.WordDb
import ua.notky.silfy.models.model.Word
import ua.notky.silfy.models.states.WordState

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

object WordMapper : Mapper<Word> {

    override fun map(input: Any): Word {
        return when (input) {
            is WordDb -> map(input)
            else -> throw IllegalStateException("Not be cast ${input::class.java.simpleName} to Word")
        }
    }

    private fun map(input: WordDb): Word {
        return Word(
            input.id,
            input.en,
            input.ua,
            WordState.getStateById(input.state),
            input.isFavourite,
            input.isBlacklist
        )
    }
}
package ua.notky.silfy.mapper.category

import ua.notky.silfy.models.dto.CategoryDto
import ua.notky.silfy.models.enums.AppLanguage
import ua.notky.silfy.models.local.CategoryLocal

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 23.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
object CategoryLocalMapper {

    fun map(input: Any, params: Params): CategoryLocal {
        return when (input) {
            is CategoryDto -> map(input, params.userId, params.language)
            else -> throw IllegalStateException("Not be cast ${input::class.java.simpleName} to CategoryLocal")
        }
    }

    fun map(input: List<Any>, params: Params): List<CategoryLocal> {
        return input.map { map(it, params) }
    }

    data class Params(
        val userId: Int,
        val language: AppLanguage
    )

    private fun map(input: CategoryDto, userId: Int, language: AppLanguage): CategoryLocal {
        return CategoryLocal(
            null,
            input.title(language),
            userId = userId
        )
    }
}
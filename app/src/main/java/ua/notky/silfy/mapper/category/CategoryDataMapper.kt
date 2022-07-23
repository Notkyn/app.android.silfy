package ua.notky.silfy.mapper.category

import ua.notky.silfy.models.dto.CategoryDto
import ua.notky.silfy.models.local.CategoryData

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 23.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
object CategoryDataMapper {

    fun map(input: Any, params: Params): CategoryData {
        return when (input) {
            is CategoryDto -> map(input, params.userId)
            else -> throw IllegalStateException("Not be cast ${input::class.java.simpleName} to CategoryData")
        }
    }

    fun map(input: List<Any>, params: Params): List<CategoryData> {
        return input.map { map(it, params) }
    }

    data class Params(
        val userId: Int
    )

    private fun map(input: CategoryDto, userId: Int): CategoryData {
        return CategoryData(
            null,
            input.title,
            userId = userId
        )
    }
}
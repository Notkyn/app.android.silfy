package ua.notky.silfy.mapper.category

import ua.notky.silfy.mapper.Mapper
import ua.notky.silfy.models.local.CategoryData
import ua.notky.silfy.models.model.Category

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 23.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
object CategoryMapper : Mapper<Category> {
    override fun map(input: Any): Category {
        return when (input) {
            is CategoryData -> map(input)
            else -> throw IllegalStateException("Not be cast ${input::class.java.simpleName} to Category")
        }
    }

    private fun map(input: CategoryData): Category {
        return Category(
            input.id,
            input.title,
            listOf()
        )
    }
}
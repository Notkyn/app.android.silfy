package ua.notky.silfy.mapper.category

import ua.notky.silfy.mapper.Mapper
import ua.notky.silfy.mapper.word.WordMapper
import ua.notky.silfy.models.local.CategoryLocal
import ua.notky.silfy.models.local.cross.CategoryWithWords
import ua.notky.silfy.models.model.Category

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 23.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
object CategoryMapper : Mapper<Category> {
    override fun map(input: Any): Category {
        return when (input) {
            is CategoryLocal -> map(input)
            is CategoryWithWords -> map(input)
            else -> throw IllegalStateException("Not be cast ${input::class.java.simpleName} to Category")
        }
    }

    private fun map(input: CategoryLocal): Category {
        return Category(
            input.id,
            input.title,
            listOf()
        )
    }
    private fun map(input: CategoryWithWords): Category {
        return Category(
            input.category.id,
            input.category.title,
            WordMapper.map(input.words)
        )
    }

}
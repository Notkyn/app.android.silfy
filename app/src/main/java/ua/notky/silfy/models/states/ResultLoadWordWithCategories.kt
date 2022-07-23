package ua.notky.silfy.models.states

import ua.notky.silfy.models.model.Category
import ua.notky.silfy.models.model.Word

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 23.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

sealed class ResultLoadWordWithCategories {
    data class Success(
        val word: Word,
        val categories: List<Category>
    ) : ResultLoadWordWithCategories()

    data class Failure(val error: Throwable?) : ResultLoadWordWithCategories()
}
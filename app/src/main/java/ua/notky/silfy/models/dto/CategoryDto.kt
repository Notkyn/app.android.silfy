package ua.notky.silfy.models.dto

import com.squareup.moshi.JsonClass

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 23.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@JsonClass(generateAdapter = true)
data class CategoryDto(
    val title: String = ""
)

package ua.notky.silfy.models.dto

import com.squareup.moshi.JsonClass
import ua.notky.silfy.models.enums.AppLanguage

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 23.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

/**
 * Default category from assets/categories.json.
 * [key] links words to the category; titles are keyed by [AppLanguage.code], [en] is the fallback.
 */
@JsonClass(generateAdapter = true)
data class CategoryDto(
    val key: String = "",
    val en: String = "",
    val uk: String? = null,
    val pl: String? = null,
    val es: String? = null,
    val de: String? = null,
    val it: String? = null,
    val pt: String? = null,
    val fr: String? = null
) {
    fun title(language: AppLanguage): String {
        val value = when (language) {
            AppLanguage.UK -> uk
            AppLanguage.PL -> pl
            AppLanguage.ES -> es
            AppLanguage.DE -> de
            AppLanguage.IT -> it
            AppLanguage.PT -> pt
            AppLanguage.FR -> fr
        }
        return value?.trim()?.takeIf { text -> text.isNotEmpty() } ?: en
    }
}

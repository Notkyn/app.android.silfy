package ua.notky.silfy.models.dto

import com.squareup.moshi.JsonClass
import ua.notky.silfy.models.enums.AppLanguage

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

/**
 * Default dictionary word from assets/words/words_x.json.
 * Translations are keyed by [AppLanguage.code]; [categories] are keys from categories.json.
 */
@JsonClass(generateAdapter = true)
data class WordDto(
    val en: String = "",
    val uk: String? = null,
    val pl: String? = null,
    val es: String? = null,
    val de: String? = null,
    val it: String? = null,
    val pt: String? = null,
    val fr: String? = null,
    val categories: List<String> = listOf()
) {
    /** `null` when the word has no translation into [language] yet */
    fun translation(language: AppLanguage): String? {
        val value = when (language) {
            AppLanguage.UK -> uk
            AppLanguage.PL -> pl
            AppLanguage.ES -> es
            AppLanguage.DE -> de
            AppLanguage.IT -> it
            AppLanguage.PT -> pt
            AppLanguage.FR -> fr
        }
        return value?.trim()?.takeIf { text -> text.isNotEmpty() }
    }
}

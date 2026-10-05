package ua.notky.silfy.validation

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

/** Letters of any translation language (incl. diacritics), apostrophes, hyphen, underscore, space */
private val TRANSLATION_REGEX = Regex("^[\\p{L}\\p{M}_ '`’ʼ\\-]+$")

/** Letters of any language, digits and _-/\| */
private val CATEGORY_NAME_REGEX = Regex("^[\\p{L}\\p{M}\\p{N}\\- _\\\\|/]+$")

private val FORBIDDEN_LETTERS_REGEX = Regex("^[^ыЫъЪ]+$")

fun checkProfileName(expect: String?): Boolean {
    return !expect?.trim().isNullOrEmpty()
}

/** English letters, apostrophe, hyphen and space */
private val WORD_EN_REGEX = Regex("^[a-zA-Z' -]+$")

fun checkWordEn(expect: String?): Boolean {
    val value = expect?.trim()

    return !value.isNullOrEmpty() && value.matches(WORD_EN_REGEX)
}

/** Translations separated by commas; empty parts ("a, , b", trailing comma) are ignored */
fun checkWordTranslation(expect: String?): Boolean {
    val list = expect?.split(",")?.map { it.trim() }?.filter { it.isNotEmpty() }

    if (list.isNullOrEmpty()) return false

    return list.all { it.matches(TRANSLATION_REGEX) && it.matches(FORBIDDEN_LETTERS_REGEX) }
}

fun checkCategoryName(expect: String?): Boolean {
    if(expect.isNullOrEmpty()) return false

    val value = expect.trim()
    if(value.isEmpty()) return false

    return value.matches(CATEGORY_NAME_REGEX) && value.matches(FORBIDDEN_LETTERS_REGEX)
}


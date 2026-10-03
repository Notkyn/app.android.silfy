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

fun checkWordEn(expect: String?): Boolean {
    val value = expect?.trim()

    return !value.isNullOrEmpty() &&
            value.matches(Regex("^[a-zA-Z ]+$"))
}

fun checkWordTranslation(expect: String?): Boolean {
    if(expect.isNullOrEmpty()) return false

    var result = true
    val list = expect.split(",")

    if(list.isEmpty()) return false

    list.forEach {
        val value = it.trim()

        if(!value.matches(TRANSLATION_REGEX) || !value.matches(FORBIDDEN_LETTERS_REGEX)) {
            result = false
        }
    }

    return result
}

fun checkCategoryName(expect: String?): Boolean {
    if(expect.isNullOrEmpty()) return false

    val value = expect.trim()
    if(value.isEmpty()) return false

    return value.matches(CATEGORY_NAME_REGEX) && value.matches(FORBIDDEN_LETTERS_REGEX)
}

fun checkCategoryIsExist(expect: String?, contains: List<String>?): Boolean {
    if(contains.isNullOrEmpty()) return true

    return !contains.contains(expect)
}

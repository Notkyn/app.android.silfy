package ua.notky.silfy.util

private val SPACES_REGEX = Regex("\\s+")

/** "  journey   home " → "Journey home": single spaces, the first letter is capital */
fun normalizeWordEn(value: String): String {
    return value.trim()
        .replace(SPACES_REGEX, " ")
        .replaceFirstChar { it.uppercaseChar() }
}

/** "подорож ,мандрівка, " → "подорож, мандрівка" */
fun normalizeTranslation(value: String): String {
    return value.split(",")
        .map { it.trim().replace(SPACES_REGEX, " ") }
        .filter { it.isNotEmpty() }
        .joinToString(", ")
}

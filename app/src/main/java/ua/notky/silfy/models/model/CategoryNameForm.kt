package ua.notky.silfy.models.model

/** 3c New / Edit category sheet: what the "Name" field shows right now */
data class CategoryNameForm(
    val isNew: Boolean = true,
    val name: String = "",
    /** Set when "Save" is pressed; cleared once the name is edited */
    val error: Error? = null
) {
    enum class Error { EMPTY, CHARS, EXISTS }
}

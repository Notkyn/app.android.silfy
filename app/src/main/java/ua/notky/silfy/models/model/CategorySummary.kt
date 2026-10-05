package ua.notky.silfy.models.model

/** 3a Categories grid card: a category with the number of its words, without the words themselves */
data class CategorySummary(
    val id: Int,
    val title: String,
    val wordCount: Int
)

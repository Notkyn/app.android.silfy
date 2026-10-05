package ua.notky.silfy.models.model

import ua.notky.silfy.models.states.WordState
import ua.notky.silfy.validation.checkWordEn
import ua.notky.silfy.validation.checkWordTranslation

/** 2c Edit / New word: what the form shows right now */
data class WordForm(
    val isNew: Boolean = true,
    val en: String = "",
    val translation: String = "",
    /** Selected categories, in the order they were added */
    val categories: List<Category> = emptyList(),
    val isFavourite: Boolean = false,
    val isBlacklist: Boolean = false,
    val state: WordState = WordState.UNKNOWN,
    /** Set when saving found the same English word; cleared once the word is edited */
    val isDuplicate: Boolean = false
) {
    /** Shown while typing: only for a non-empty field */
    val isEnError: Boolean get() = en.isNotBlank() && !checkWordEn(en)

    val isTranslationError: Boolean get() = translation.isNotBlank() && !checkWordTranslation(translation)

    /** The "Save word" button is disabled until the form is valid */
    val canSave: Boolean get() = checkWordEn(en) && checkWordTranslation(translation) && !isDuplicate
}

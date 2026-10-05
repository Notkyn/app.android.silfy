package ua.notky.silfy.session

import ua.notky.silfy.models.model.Word

/** One question of a training session (4c–4h) */
sealed class SessionQuestion {
    abstract val word: Word

    /** The big word on the ink card */
    abstract val prompt: String

    /** What counts as the right answer; shown in "Not quite — it's …" */
    abstract val answer: String

    /** Choose / Letters: English → profile language; Type: the other way round */
    val isFromEnglish: Boolean get() = this !is Type

    /** 4c–4e: 2–4 translations, one of them right; an answer counts at the first tap */
    data class Choose(
        override val word: Word,
        override val prompt: String,
        override val answer: String,
        val options: List<String>
    ) : SessionQuestion()

    /** 4f: the translation without spaces, built from shuffled letter tiles (+2 extra on Hard) */
    data class Letters(
        override val word: Word,
        override val prompt: String,
        override val answer: String,
        val tiles: List<String>
    ) : SessionQuestion()

    /** 4g/4h: the translation is shown, the English word is typed (Hard only) */
    data class Type(
        override val word: Word,
        override val prompt: String,
        override val answer: String
    ) : SessionQuestion()
}

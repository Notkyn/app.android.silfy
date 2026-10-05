package ua.notky.silfy.session

import ua.notky.silfy.models.model.Word
import ua.notky.silfy.ui.view.level.MAX_WORD_LEVEL
import ua.notky.silfy.ui.view.level.level
import kotlin.random.Random

/**
 * Builds the questions of a training session and checks the answers. No Android, no database.
 *
 * - Easy: Choose or Letters, English → profile language, every word equally often.
 * - Hard: Type (profile language → English), Choose or Letters; a word comes up with weight 5 − level,
 *   so weak words more often; Letters gets 2 extra letters.
 *
 * @param pool words of the session (the settings filter), not empty
 * @param dictionary all words of the profile: wrong options and extra letters come from here
 */
class SessionEngine(
    pool: List<Word>,
    private val dictionary: List<Word>,
    private val isHard: Boolean,
    private val random: Random = Random.Default
) {
    private val pool: MutableList<Word> = pool.toMutableList()
    private var lastWordId: Int? = null

    init {
        require(pool.isNotEmpty()) { "Session pool is empty" }
    }

    val poolSize: Int get() = pool.size

    fun next(): SessionQuestion {
        val word = pickWord()
        lastWordId = word.id

        val modes = if (isHard) listOf(Mode.TYPE, Mode.CHOOSE, Mode.LETTERS) else listOf(Mode.CHOOSE, Mode.LETTERS)
        return when (modes[random.nextInt(modes.size)]) {
            Mode.CHOOSE -> choose(word) ?: letters(word)
            Mode.LETTERS -> letters(word)
            Mode.TYPE -> type(word)
        }
    }

    /** The word got a new level after an answer: Hard weights follow it */
    fun updateWord(word: Word) {
        val index = pool.indexOfFirst { it.id == word.id }
        if (index >= 0) pool[index] = word
    }

    fun isCorrect(question: SessionQuestion, answer: String): Boolean {
        return when (question) {
            is SessionQuestion.Choose -> answer == question.answer
            is SessionQuestion.Letters -> answer.equals(question.answer, ignoreCase = true)
            is SessionQuestion.Type -> normalizeEnglish(answer) == normalizeEnglish(question.answer)
        }
    }

    /** Not the same word twice in a row while there is a choice */
    private fun pickWord(): Word {
        val candidates = pool.filter { it.id != lastWordId }.ifEmpty { pool }
        if (!isHard) return candidates[random.nextInt(candidates.size)]

        val weights = candidates.map { (MAX_WORD_LEVEL + 1) - it.state.level }
        var point = random.nextInt(weights.sum())
        candidates.forEachIndexed { index, word ->
            point -= weights[index]
            if (point < 0) return word
        }
        return candidates.last()
    }

    /** null — no other translation to offer as a wrong option */
    private fun choose(word: Word): SessionQuestion.Choose? {
        val answer = firstTranslation(word)
        val ownTranslations = translations(word).map { it.lowercase() }.toSet()

        val wrong = dictionary.asSequence()
            .filter { it.id != word.id }
            .map { firstTranslation(it) }
            // A wrong option must not be one of this word's own translations
            .filter { it.isNotEmpty() && it.lowercase() !in ownTranslations }
            .distinctBy { it.lowercase() }
            .toList()
            .shuffled(random)
            .take(CHOOSE_OPTIONS - 1)

        if (wrong.isEmpty()) return null
        return SessionQuestion.Choose(word, word.en, answer, (wrong + answer).shuffled(random))
    }

    private fun letters(word: Word): SessionQuestion.Letters {
        val answer = firstTranslation(word).filterNot { it.isWhitespace() }.lowercase()
        val tiles = answer.map { it.toString() }.toMutableList()
        if (isHard) tiles += extraLetters()
        return SessionQuestion.Letters(word, word.en, answer, tiles.shuffled(random))
    }

    private fun type(word: Word): SessionQuestion.Type {
        return SessionQuestion.Type(word, firstTranslation(word), word.en)
    }

    /** Letters of the profile language: taken from other translations, so any alphabet works */
    private fun extraLetters(): List<String> {
        val letters = dictionary.shuffled(random)
            .take(EXTRA_LETTERS_SOURCE_WORDS)
            .flatMap { firstTranslation(it).lowercase().filter { char -> char.isLetter() }.toList() }
        if (letters.isEmpty()) return emptyList()
        return List(EXTRA_LETTERS) { letters[random.nextInt(letters.size)].toString() }
    }

    private enum class Mode { CHOOSE, LETTERS, TYPE }

    companion object {
        private const val CHOOSE_OPTIONS = 4
        private const val EXTRA_LETTERS = 2
        private const val EXTRA_LETTERS_SOURCE_WORDS = 10

        private val SPACES = Regex("\\s+")

        fun translations(word: Word): List<String> {
            return word.translation.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        }

        /** "подорож, мандрівка" → "подорож": the one used in questions */
        fun firstTranslation(word: Word): String = translations(word).firstOrNull() ?: word.translation.trim()

        /** Case, extra spaces and the typographic apostrophe do not matter */
        fun normalizeEnglish(value: String): String {
            return value.trim().replace(SPACES, " ").replace('’', '\'').lowercase()
        }
    }
}

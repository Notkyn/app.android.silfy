package ua.notky.silfy.models.enums

import ua.notky.silfy.R
import kotlin.random.Random

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 09.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
enum class GoMode(
    val value: Int
) {
    SELECT(R.string.text_go_select_mode),
    WRITE(R.string.text_go_write_mode),
    SYMBOL(R.string.text_go_symbol);

    fun getRandomMode(easyDifficult: Boolean = true): GoMode {
        val range = Random.nextInt(10)
        return if(easyDifficult) {
            getModeByEasy(range)
        } else {
            getModeByHard(range)
        }
    }

    private fun getModeByEasy(range: Int): GoMode {
        // SELECT - 01234
        // SYMBOL - 5678
        // WRITE  - 9
        return when {
            range < 5 -> SELECT
            range == 9 -> WRITE
            else -> SYMBOL
        }
    }

    private fun getModeByHard(range: Int): GoMode {
        // SELECT - 012345
        // SYMBOL - 6789
        return when {
            range < 6 -> SELECT
            else -> SYMBOL
        }
    }
}
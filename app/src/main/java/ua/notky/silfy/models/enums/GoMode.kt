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

    fun getRandomMode(): GoMode {
        val range = Random.nextInt(10)

        // 01234 5678 9
        return when {
            range < 5 -> SELECT
            range == 9 -> WRITE
            else -> SYMBOL
        }
    }
}
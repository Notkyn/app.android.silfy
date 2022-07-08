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
        return values()[Random.nextInt(values().size)]
    }
}
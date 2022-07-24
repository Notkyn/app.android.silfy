package ua.notky.silfy.models.enums

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 02.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
enum class DifficultType(
    val id: Int,
    val value: String
) {
    EASY(1, "easy"),
    HARD(2, "hard");

    companion object {
        fun getById(id: Int): DifficultType {
            return when (id) {
                HARD.id -> HARD
                else -> EASY
            }
        }
    }
}
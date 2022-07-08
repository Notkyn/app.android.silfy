package ua.notky.silfy.models.enums

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 04.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
enum class TrainingDurationType(
    val type: String,
    val seconds: Long
) {
    FIVE("five",  300),
    TEN("ten",  600),
    THIRTY("thirty",  1800),
    INFINITY("infinity",  0)
}
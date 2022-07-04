package ua.notky.silfy.models.enums

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 04.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
enum class TrainingDurationType(
    val type: String
) {
    FIVE("five"),
    TEN("ten"),
    THIRTY("thirty"),
    INFINITY("infinity")
}
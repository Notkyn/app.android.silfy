package ua.notky.silfy.models.enums

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 04.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
enum class TrainingDurationType(
    val id: Int,
    val type: String,
    val seconds: Long
) {
    FIVE(1,"five",  300),
    TEN(2, "ten",  600),
    THIRTY(3, "thirty",  1800),
    INFINITY(4, "infinity",  0)
}
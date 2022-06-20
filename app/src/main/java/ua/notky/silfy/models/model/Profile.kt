package ua.notky.silfy.models.model

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

data class Profile(
    val id: Int?,
    val firstName: String?,
    val lastName: String?,
    val avatar: String?,
    val email: String?,
    val createTime: Long?
)
package ua.notky.base.failure

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

data class Failure(
    val type: Int?,
    val category: Category = Category.EXCEPTION,
    val msg: String?,
    var resIdMsg: Int?,
    val exception: Throwable? = null
) {
    enum class Category { APP, API, HTTP, EXCEPTION }
}
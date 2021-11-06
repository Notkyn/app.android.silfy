package ua.notky.base.validation

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

data class ValidationModel(
    val type: Int,
    val expect: String? = null,
    val actual: String? = null,
    val ignore: String? = null,
    val boolean: Boolean? = null,
    val obj: Any? = null
)
package ua.notky.base.validation

import ua.notky.base.changeable.ValidationMode

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

data class ValidationModel(
    val mode: ValidationMode,
    val expect: String? = null,
    val actual: String? = null,
    val ignore: String? = null,
    val boolean: Boolean? = null,
    val obj: Any? = null
)
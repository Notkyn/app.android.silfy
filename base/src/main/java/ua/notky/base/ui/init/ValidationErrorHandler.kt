package ua.notky.base.ui.init

import ua.notky.base.validation.ValidationError

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 06.11.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */
interface ValidationErrorHandler {
    fun setValidationErrors(errors: List<ValidationError>) {}
    fun clearValidationErrors() {}
}
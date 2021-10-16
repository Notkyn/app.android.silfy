package ua.notky.base.validation

import ua.notky.base.changeable.ValidationError
import ua.notky.base.changeable.ValidationMode

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

interface ValidationService {
    fun validateData(data: List<ValidationModel>): List<ValidationError>
    fun setValidateMsg(modes: List<ValidationMode>): List<ValidationError>
}
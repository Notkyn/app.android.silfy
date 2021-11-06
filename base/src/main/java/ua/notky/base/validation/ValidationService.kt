package ua.notky.base.validation

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

interface ValidationService {
    fun validateData(data: List<ValidationModel>): List<ValidationError>
    fun setValidateMsg(types: List<Int>): List<ValidationError>
    fun createError(type: Int): ValidationError?
    fun chooseValidation(list: List<ValidationModel>)
}
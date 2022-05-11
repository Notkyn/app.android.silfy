package ua.notky.base.validation

import android.content.Context

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

abstract class BaseValidationService : ValidationService {
    protected abstract val context: Context
    protected var errors: MutableList<ValidationError> = mutableListOf()

    override fun validateData(data: List<ValidationModel>): List<ValidationError> {
        errors = mutableListOf()
        chooseValidation(data)
        return errors
    }

    fun checkValue(
        mode: Int,
        block: () -> Boolean
    ) {
        if (!block()) {
            addError(mode)
        }
    }

    override fun setValidateMsg(types: List<Int>): List<ValidationError> {
        errors = mutableListOf()
        types.forEach { addError(it) }
        return errors
    }

    private fun addError(mode: Int) {
        createError(mode)?.let { errors.add(it) }
    }

    abstract fun createError(type: Int): ValidationError?
    abstract fun chooseValidation(list: List<ValidationModel>)
}
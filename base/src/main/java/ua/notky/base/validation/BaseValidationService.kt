package ua.notky.base.validation

import android.content.Context
import ua.notky.base.changeable.ValidationError
import ua.notky.base.changeable.ValidationMode

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

    protected abstract fun chooseValidation(list: List<ValidationModel>)

    fun checkValue(
        mode: ValidationMode,
        block: () -> Boolean
    ) {
        if (!block()) {
            addError(mode)
        }
    }

    override fun setValidateMsg(modes: List<ValidationMode>): List<ValidationError> {
        errors = mutableListOf()
        modes.forEach { addError(it) }
        return errors
    }

    protected fun addError(mode: ValidationMode) {
        errors.add(createError(mode))
    }

    protected abstract fun createError(mode: ValidationMode): ValidationError
}
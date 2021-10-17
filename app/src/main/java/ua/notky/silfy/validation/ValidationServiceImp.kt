package ua.notky.silfy.validation

import android.content.Context
import ua.notky.base.changeable.ValidationError
import ua.notky.base.changeable.ValidationMode
import ua.notky.base.validation.BaseValidationService
import ua.notky.base.validation.ValidationModel
import ua.notky.silfy.R
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class ValidationServiceImp @Inject constructor(
    override val context: Context
    ) : BaseValidationService() {

    override fun chooseValidation(list: List<ValidationModel>) {
        list.forEach { model ->
            when (model.mode) {
                ValidationMode.EMAIL -> checkValue(model.mode) { checkEmailField(model.expect) }
            }
        }
    }

    override fun createError(mode: ValidationMode): ValidationError {
        return when (mode) {
            ValidationMode.EMAIL -> ValidationError.Email(context.getString(R.string.error_wrong_email))
        }
    }
}
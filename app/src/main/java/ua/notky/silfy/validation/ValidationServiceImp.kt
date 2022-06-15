package ua.notky.silfy.validation

import android.content.Context
import ua.notky.base.validation.BaseValidationService
import ua.notky.base.validation.ValidationError
import ua.notky.base.validation.ValidationModel
import ua.notky.silfy.R
import ua.notky.silfy.config.*
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
            when (model.type) {
                VALIDATION_EMAIL -> checkValue(model.type) { checkEmailField(model.expect) }
                VALIDATION_WORD_EU -> checkValue(model.type) { checkWordEn(model.expect) }
                VALIDATION_WORD_UA -> checkValue(model.type) { checkWordUa(model.expect) }
                VALIDATION_CATEGORY_NAME ->
                    checkValue(model.type) { checkCategoryName(model.expect) }
                VALIDATION_CATEGORY_IS_EXIST ->
                    checkValue(model.type) { checkCategoryIsExist(model.expect, model.contains) }
            }
        }
    }

    override fun createError(type: Int): ValidationError? {
        return when (type) {
            VALIDATION_EMAIL -> ValidationError(VALIDATION_EMAIL, context.getString(R.string.error_wrong_email))
            VALIDATION_WORD_EU -> ValidationError(VALIDATION_WORD_EU, context.getString(R.string.error_wrong_word_en))
            VALIDATION_WORD_UA -> ValidationError(VALIDATION_WORD_UA, context.getString(R.string.error_wrong_word_ua))
            VALIDATION_CATEGORY_NAME -> ValidationError(VALIDATION_CATEGORY_NAME, context.getString(R.string.error_wrong_category_name))
            VALIDATION_CATEGORY_IS_EXIST -> ValidationError(VALIDATION_CATEGORY_IS_EXIST, context.getString(R.string.error_is_category_exist))
            else -> null
        }
    }
}
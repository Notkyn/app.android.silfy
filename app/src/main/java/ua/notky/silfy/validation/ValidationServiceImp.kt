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
                VALIDATION_PROFILE_NAME -> checkValue(model.type) { checkProfileName(model.expect) }
                VALIDATION_WORD_EU -> checkValue(model.type) { checkWordEn(model.expect) }
                VALIDATION_WORD_TRANSLATION -> checkValue(model.type) { checkWordTranslation(model.expect) }
            }
        }
    }

    override fun createError(type: Int): ValidationError? {
        return when (type) {
            VALIDATION_PROFILE_NAME -> ValidationError(VALIDATION_PROFILE_NAME, context.getString(R.string.error_wrong_profile_name))
            VALIDATION_WORD_EU -> ValidationError(VALIDATION_WORD_EU, context.getString(R.string.error_wrong_word_en))
            VALIDATION_WORD_TRANSLATION -> ValidationError(VALIDATION_WORD_TRANSLATION, context.getString(R.string.error_wrong_word_translation))
            else -> null
        }
    }
}
package ua.notky.silfy.viewmodel.auth

import dagger.hilt.android.lifecycle.HiltViewModel
import ua.notky.base.validation.ValidationModel
import ua.notky.base.validation.ValidationService
import ua.notky.base.viewmodel.BaseValidationViewModel
import ua.notky.silfy.BuildConfig
import ua.notky.silfy.config.ACTION_TO_MAIN
import ua.notky.silfy.config.VALIDATION_EMAIL
import ua.notky.silfy.models.observable.AuthModel
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@HiltViewModel
class AuthViewModel @Inject constructor(
    override val validation: ValidationService
    ) : BaseValidationViewModel() {
    private val model: AuthModel = AuthModel()

    fun getEmptyModel(): AuthModel {
        model.email.set("")

        @Deprecated(message = "for test")
        if(BuildConfig.DEBUG) {
            model.email.set("test@test.com")
        }

        return model
    }

    fun onContinue() {
        if(isValidEmail()) {
            setAction(ACTION_TO_MAIN)
        }
    }

    private fun isValidEmail(): Boolean {
        return addValidateData(listOf(
            ValidationModel(VALIDATION_EMAIL, model.email.get())
        ))
    }
}
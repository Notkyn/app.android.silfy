package ua.notky.base.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ua.notky.base.changeable.ValidationError
import ua.notky.base.changeable.ValidationMode
import ua.notky.base.validation.ValidationModel
import ua.notky.base.validation.ValidationService

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

abstract class BaseValidationViewModel : BaseViewModel() {
    protected abstract val validation: ValidationService

    private val errors: MutableLiveData<List<ValidationError>> = MutableLiveData(listOf())
    fun getValidationErrors(): LiveData<List<ValidationError>> {
        return errors
    }

    private fun clearValidationErrors() {
        errors.value = listOf()
    }

    protected fun addValidateData(list: List<ValidationModel>): Boolean {
        val tempErrors = validation.validateData(list)
        errors.postValue(tempErrors)

        return tempErrors.isEmpty()
    }

    protected fun addValidateDataForMsg(modes: List<ValidationMode>) {
        val tempErrors = validation.setValidateMsg(modes)
        errors.postValue(tempErrors)
    }

    override fun init() {
        super.init()
        clearValidationErrors()
    }
}
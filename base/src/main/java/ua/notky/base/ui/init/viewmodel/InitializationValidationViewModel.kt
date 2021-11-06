package ua.notky.base.ui.init.viewmodel

import androidx.lifecycle.LiveData
import ua.notky.base.validation.ValidationError

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 06.11.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */
interface InitializationValidationViewModel {
    fun getValidationErrors(): LiveData<List<ValidationError>>
}
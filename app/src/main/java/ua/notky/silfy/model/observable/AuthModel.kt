package ua.notky.silfy.model.observable

import androidx.databinding.ObservableField

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */
data class AuthModel(
    val email: ObservableField<String> = ObservableField("")
)
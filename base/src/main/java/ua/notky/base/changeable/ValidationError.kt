package ua.notky.base.changeable

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

sealed class ValidationError {
    data class Email(val msg: String) : ValidationError()
    data class WordEn(val msg: String) : ValidationError()
    data class WordUa(val msg: String) : ValidationError()






    data class Other(val msg: String) : ValidationError()
}
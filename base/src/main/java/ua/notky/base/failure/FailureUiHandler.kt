package ua.notky.base.failure

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 06.11.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */
interface FailureUiHandler {
    fun handleFailure(failure: Failure?) {}
}
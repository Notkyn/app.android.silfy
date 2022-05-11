package ua.notky.base.ui.init

import ua.notky.base.failure.Failure

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 06.11.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */
interface FailureHandler {
    fun handleFailure(failure: Failure?) {}
}
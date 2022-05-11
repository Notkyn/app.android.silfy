package ua.notky.base.failure.handler

import ua.notky.base.failure.Failure

/**
 * @project HideExpert
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 22.04.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
interface ExceptionFailureHandler {
    // An implementation is required to create an error for other exceptions
    fun createExceptionFailure(msg: String?, exception: Throwable?): Failure
}
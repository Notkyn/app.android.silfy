package ua.notky.base.extension

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

fun CoroutineScope.safeLaunch(launchBody: suspend () -> Unit): Job {
    val coroutineExceptionHandler = CoroutineExceptionHandler {
            _, throwable ->
        // handle thrown exceptions from coroutine scope
        throwable.printStackTrace()
    }

    return this.launch(coroutineExceptionHandler) {
        launchBody.invoke()
    }
}
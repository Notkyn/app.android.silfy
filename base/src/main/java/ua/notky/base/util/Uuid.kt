package ua.notky.base.util

import java.util.*

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

fun generateUuid(): String {
    return UUID.randomUUID().toString()
}
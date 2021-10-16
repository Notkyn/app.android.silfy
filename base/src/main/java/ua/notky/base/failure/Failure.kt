package ua.notky.base.failure

import ua.notky.base.changeable.FailureType

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

class Failure constructor(
    val type: FailureType?,
    val msg: String?,
    var localizeMsg: String? = null
) {
    constructor(msg: String?) : this(null, msg)
}
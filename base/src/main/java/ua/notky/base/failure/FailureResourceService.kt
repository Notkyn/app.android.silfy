package ua.notky.base.failure

import ua.notky.base.changeable.FailureType

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

interface FailureResourceService {
    fun getLocalizeMsg(type: FailureType?): String?
}
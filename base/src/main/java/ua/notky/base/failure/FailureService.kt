package ua.notky.base.failure

import androidx.lifecycle.LiveData

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

interface FailureService {
    fun getLocalizeMsg(type: Int?): String?
    fun setFailure(failure: Failure?)
    fun clearData()
    fun getFailureLiveData(): LiveData<Failure?>
}
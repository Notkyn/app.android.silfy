package ua.notky.base.failure

import androidx.lifecycle.LiveData
import ua.notky.base.network.api.response.NetworkError

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
    fun createApiFailure(httpCode: Int?, code: Int?, msg: String?): Failure

    // Failures
    fun createNetworkFailure(error: NetworkError)
    fun createExceptionFailure(ex: Throwable)
}
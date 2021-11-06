package ua.notky.base.failure

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 06.11.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

abstract class BaseFailureService : FailureService {
    protected abstract val context: Context

    private val _failureLiveData: MutableLiveData<Failure?> = MutableLiveData()

    override fun getFailureLiveData(): LiveData<Failure?> {
        return _failureLiveData
    }

    override fun setFailure(failure: Failure?) {
        _failureLiveData.postValue(failure)
    }

    override fun clearData() {
        _failureLiveData.value = null
    }
}
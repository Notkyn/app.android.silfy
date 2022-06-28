package ua.notky.silfy.viewmodel.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.models.model.DictionaryInfo
import ua.notky.silfy.util.help.getTempDictionaryInfo

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 29.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class MenuDictionaryViewModel : BaseViewModel() {

    private val _dictionaryInfo: MutableLiveData<DictionaryInfo> = MutableLiveData()
    val dictionaryInfo: LiveData<DictionaryInfo> = _dictionaryInfo

    fun fetchData() {
        val info = getTempDictionaryInfo()

        _dictionaryInfo.postValue(info)
    }
}
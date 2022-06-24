package ua.notky.silfy.viewmodel.profile

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ua.notky.base.viewmodel.BaseViewModel
import ua.notky.silfy.models.observable.EditProfileModel
import ua.notky.silfy.tools.image.LoadImageState
import ua.notky.silfy.tools.image.LoadImageUseCase

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 22.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class EditProfileViewModel : BaseViewModel() {
    val model = EditProfileModel()

    private val _imagePath: MutableLiveData<String> = MutableLiveData()
    val imagePath: LiveData<String> = _imagePath

    fun updateModel(firstName: String?, lastName: String?) {
        model.firstName.set(firstName)
        model.lastName.set(lastName)
    }

    fun updatePhoto(path: String?) {
        model.photoPath.set(path)
        model.isLoading.set(false)
    }

    fun saveImage() {
        model.isLoading.set(true)
        viewModelScope.launch {
            delay(2000)
            _imagePath.postValue(model.photoPath.get())
            model.isLoading.set(false)
        }
    }

    fun loadImage(context: Context, uri: Uri?) {
        model.isLoading.set(true)
        viewModelScope.launch(Dispatchers.IO) {
            when (val state = LoadImageUseCase.loadImage(context, uri)) {
                is LoadImageState.Success -> handleSuccessLoading(state.uri)
                is LoadImageState.EmptyUri -> handleFailureLoading()
                is LoadImageState.Error -> handleFailureLoading()
            }
        }
    }

    private fun handleSuccessLoading(uri: Uri) {
        model.photoPath.set(uri.toString())
        model.isLoading.set(false)
    }

    private fun handleFailureLoading() {
        model.photoPath.set("")
        model.isLoading.set(false)
    }
}
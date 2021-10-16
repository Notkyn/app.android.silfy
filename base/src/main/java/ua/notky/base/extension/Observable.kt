package ua.notky.base.extension

import androidx.databinding.Observable
import androidx.databinding.ObservableField

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

fun <T : Any> ObservableField<T>.requireNotNull(): T {
    return get() ?: throw IllegalStateException("Required value $this was null.")
}

inline fun <reified T : Observable> T.addOnPropertyChanged(crossinline callback: (T) -> Unit) =
    object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(observable: Observable?, i: Int) =
            callback(observable as T)
    }.also { addOnPropertyChangedCallback(it) }

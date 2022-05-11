package ua.notky.base.viewmodel.state

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 11.05.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
object BindingStateModel {

    @JvmStatic
    @BindingAdapter("state")
    fun <M> bindingStateModel(view: View, state: M?) {
        state?.let { view.setStateModel(it) }
    }

    private fun <M> View.setStateModel(state: M) {
        this.castToStateView<M>()?.setState(state)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <M> View.castToStateView(): StateAttachModel<M>? {
        return try {
            if (this is StateAttachModel<*>) {
                this as StateAttachModel<M>
            } else {
                throw IllegalStateException("${this::class.java.simpleName} is not a StateAttachModel")
            }
        } catch (ex: Exception) {
            throw IllegalStateException("Don`t cast ${this::class.java.simpleName} to StateAttachModel")
        }
    }
}
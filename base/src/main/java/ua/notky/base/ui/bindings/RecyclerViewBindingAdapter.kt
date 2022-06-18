package ua.notky.base.ui.bindings

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import ua.notky.base.ui.adapter.RecyclerCollectionAdapter

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 18.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
object RecyclerViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("set_data")

    fun <T> bindingSetData(view: RecyclerView, words: List<T>?) {
        words?.let {
            val adapter = view.castAdapterToCollection<T>()
            adapter.clearAndAddAll(it)

        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> RecyclerView.castAdapterToCollection(): RecyclerCollectionAdapter<T> {
        return try {
            if (this.adapter is RecyclerCollectionAdapter<*>) {
                adapter as RecyclerCollectionAdapter<T>
            } else {
                throw IllegalStateException("${this::class.java.simpleName} is not a RecyclerCollectionAdapter")
            }
        } catch (ex: Exception) {
            throw IllegalStateException("Don`t cast ${this::class.java.simpleName} to RecyclerCollectionAdapter")
        }
    }
}
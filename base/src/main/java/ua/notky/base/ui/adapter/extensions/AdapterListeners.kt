package ua.notky.base.ui.adapter.extensions

import android.view.View
import ua.notky.base.ui.adapter.RecyclerCollectionAdapter
import ua.notky.base.ui.adapter.listener.*

/**
 * @project Telesim
 * @author Stanislav Humeniuk on 8/12/20
 * @email stanislav.humeniuk@gmail.com
 */

// ItemClickListener
inline fun <M> RecyclerCollectionAdapter<M>.doOnItemViewClick(crossinline action: (item: M, view: View?) -> Unit) =
    addItemClickListener(onItemViewClick = action)

inline fun <M> RecyclerCollectionAdapter<M>.doOnViewClick(crossinline action: (view: View?) -> Unit) =
    addItemClickListener(onViewClick = action)

inline fun <M> RecyclerCollectionAdapter<M>.doOnItemClick(crossinline action: (item: M) -> Unit) =
    addItemClickListener(onItemClick = action)

inline fun <M> RecyclerCollectionAdapter<M>.addItemClickListener(
    crossinline onItemViewClick: (item: M, view: View?) -> Unit = { _, _ -> },
    crossinline onViewClick: (view: View?) -> Unit = { _ -> },
    crossinline onItemClick: (item: M) -> Unit = {},
): OnItemClickListener<M> {

    val listener = object : OnItemClickListener<M> {
        override fun onItemClick(item: M, view: View?) {
            onItemViewClick.invoke(item, view)
            onItemClick.invoke(item)
            onViewClick.invoke(view)
        }
    }

    setOnItemClickListener(listener)

    return listener
}

// ItemLongClickListener
inline fun <M> RecyclerCollectionAdapter<M>.doOnItemLongClick(crossinline action: (item: M) -> Unit) =
    addItemLongClickListener(onItemLongClick = action)


inline fun <M> RecyclerCollectionAdapter<M>.addItemLongClickListener(
    crossinline onItemLongClick: (item: M) -> Unit = {},
): OnItemLongClickListener<M> {

    val listener = object : OnItemLongClickListener<M> {
        override fun onItemLongClick(item: M, view: View) {
            onItemLongClick.invoke(item)
        }
    }

    setOnItemLongClickListener(listener)

    return listener
}

// RootClickListener
inline fun <M> RecyclerCollectionAdapter<M>.doOnRootClick(
    crossinline action: (item: M) -> Unit
) = addRootClickListener(onRootClick = action)

inline fun <M> RecyclerCollectionAdapter<M>.addRootClickListener(
    crossinline onRootClick: (item: M) -> Unit = {},
): OnRootClickListener<M> {

    val listener = object : OnRootClickListener<M> {
        override fun onRootClick(model: M) {
            onRootClick.invoke(model)
        }
    }

    setOnRootClickListener(listener)

    return listener
}


inline fun <M> RecyclerCollectionAdapter<M>.doOnActionDelete(
    crossinline action: (item: M) -> Unit = {}
): OnRecyclerActionDeleteListener<M> {
    val listener = object : OnRecyclerActionDeleteListener<M> {
        override fun onDelete(item: M) {
            action.invoke(item)
        }
    }

    setOnActionDeleteListener(listener)

    return listener
}

inline fun <M> RecyclerCollectionAdapter<M>.doOnActionEdit(
    crossinline action: (item: M) -> Unit = {}
): OnRecyclerActionEditListener<M> {
    val listener = object : OnRecyclerActionEditListener<M> {
        override fun onEdit(item: M) {
            action.invoke(item)
        }
    }

    setOnActionEditListener(listener)

    return listener
}